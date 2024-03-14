package org.apache.camel.learn.Datos;

public class Pago {
    private String ID;
    private int Mes;
    private int Valor;
    private int DiasPago;

    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public int getMes() {
        return Mes;
    }
    public void setMes(int mes) {
        Mes = mes;
    }
    public int getValor() {
        return Valor;
    }
    public void setValor(int valor) {
        Valor = valor;
    }
    public int getDiasPago() {
        return DiasPago;
    }
    public void setDiasPago(int diasPago) {
        DiasPago = diasPago;
    }
    
}
