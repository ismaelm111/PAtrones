package org.apache.camel.learn;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.learn.Datos.Cliente;
import org.apache.camel.learn.Datos.Facturado;
import org.apache.camel.learn.Datos.Pago;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcesarArchivo implements Processor {

    public static final Logger log = LogManager.getLogger(ProcesarArchivo.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        int clientes = 0;
        int facturadosCorrectos = 0;
        int facturadosIncorrectos = 0;
        int pagoCorrectos = 0;
        int pagoIncorrectos = 0;

        String nombreArchivo = (String) exchange.getIn().getHeader("CamelFileNameOnly");

        log.info("-------------------------------------------------------");
        log.info("-------- Lectura del archivo" + nombreArchivo + " ------");
        log.info("-------------------------------------------------------");

        var lista = (ArrayList) exchange.getIn().getBody();
        clientes = lista.size();
        var cabacera = (ArrayList) lista.get(0);
        int posbill = 0;
        int cantidadbill = 0;
        for (int i = 0; i < cabacera.size(); i++) {
            if (cabacera.get(i).toString().contains("BILL_")) {
                posbill = posbill != 0 ? posbill : i;
                cantidadbill++;
            }
        }
        String sentenciaCliente = "Insert into Cliente Values ";
        String sentencia = "";
        String sentenciafactura = "Insert into Facturado Values ";
        String sentencia1 = "";
        String sentenciapago = "Insert into Pago Values ";
        String sentencia2 = "";

        ArrayList<Cliente> listCliente = new ArrayList<>();
        for (int j = 1; j < lista.size(); j++) {
            var cliente = new Cliente();
            var fila = (ArrayList) lista.get(j);
            cliente.setID(fila.get(0).toString()); // ID
            cliente.setLIMIT_BAL(fila.get(1).toString()); // LIMIT_BAL
            cliente.setSEX(fila.get(2).toString()); // SEX
            cliente.setEDUCATION(fila.get(3).toString()); // EDUCATION
            cliente.setMARRIAGE(fila.get(4).toString()); // MARRIAGE
            cliente.setAGE(fila.get(5).toString()); // AGE
            cliente.setDPNMONTH(fila.get(fila.size() - 1).toString()); // default payment next month

            sentencia = (sentencia == "" ? "("
                    : sentencia + ",(")
                    +
                    cliente.getID() + "," +
                    cliente.getLIMIT_BAL() + "," +
                    cliente.getSEX() + "," +
                    cliente.getEDUCATION() + "," +
                    cliente.getMARRIAGE() + "," +
                    cliente.getAGE() +
                    ")";

            ArrayList<Facturado> BILL = new ArrayList<>();
            ArrayList<Pago> PAYMENT = new ArrayList<>();

            for (int k = 0; k < cantidadbill; k++) {
                int bill_ = Integer.parseInt(fila.get(posbill + k).toString());
                if (bill_ > 0) {
                    Facturado billFacturado = new Facturado();
                    billFacturado.setID(cliente.getID());
                    billFacturado.setMes(k + 1);
                    billFacturado.setValor(bill_);
                    BILL.add(billFacturado);
                    sentencia1 = (sentencia1 == "" ? "("
                            : sentencia1 + ",(")
                            +
                            (k + 1) + "," +
                            billFacturado.getID() + "," +
                            billFacturado.getMes() + "," +
                            billFacturado.getValor() +
                            ")";
                    facturadosCorrectos++;

                } else {
                    String mensajeValidacion = "Registro con ID: " + cliente.getID()
                            + " - Error Validacion: El valor facturado del mes " + (k+1) + " no puede ser menor o igual a cero.";
                    log.info(mensajeValidacion);
                    facturadosIncorrectos++;

                }

                int payment_ = Integer.parseInt(fila.get(posbill + k +
                        cantidadbill).toString());
                if (payment_ > 0) {
                    Pago paymentPago = new Pago();
                    paymentPago.setID(cliente.getID());
                    paymentPago.setMes(k + 1);
                    paymentPago.setValor(Integer.parseInt(fila.get(posbill + k +
                            cantidadbill).toString()));
                    paymentPago.setDiasPago(Integer.parseInt(fila.get(posbill + k - cantidadbill).toString()));
                    PAYMENT.add(paymentPago);
                    // dias
                    sentencia2 = (sentencia2 == "" ? "("
                            : sentencia2 + ",(") +
                            (k + 1) + "," +
                            paymentPago.getID() + "," +
                            paymentPago.getMes() + "," +
                            paymentPago.getValor() + "," +
                            paymentPago.getDiasPago() +
                            ")";
                    pagoCorrectos++;
                } else {
                    String mensajeValidacion = "Registro con ID: " + cliente.getID()
                            + " - Error validacion: El valor del Pago del mes " + (k+1) +" no puede ser menor o igual a cero.";
                    log.info(mensajeValidacion);
                    pagoIncorrectos++;
                }

                cliente.setBILL(BILL);
                cliente.setPAYMENT(PAYMENT);
                listCliente.add(cliente);
            }
        }       

        log.info("-------------------------------------");
        log.info("Total de Clientes: " + (clientes-1));
        log.info("-------------------------------------");
        log.info("Registros de factura correctos: " + facturadosCorrectos);
        log.info("Registros de factura incorrectos: " + facturadosIncorrectos);
        log.info("Total Registros de factura: " + (facturadosIncorrectos+ facturadosCorrectos));
        log.info("-------------------------------------");
        log.info("Registros de pago correctos: " + pagoCorrectos);
        log.info("Registros de pago incorrectos: " + pagoIncorrectos);
        log.info("Total Registros de pago: " + (pagoCorrectos+ pagoIncorrectos));
        log.info("-------------------------------------");
        log.info("------ Fin Lectura del archivo ------");
        log.info("-------------------------------------");
        sentenciaCliente = sentenciaCliente + sentencia;
        sentenciafactura = sentenciafactura + sentencia1;
        sentenciapago = sentenciapago + sentencia2;
        String sentenciafinal = sentenciaCliente + ";" + sentenciafactura + ";" + sentenciapago + ";";
        exchange.getIn().setBody(sentenciafinal);
    }

}
