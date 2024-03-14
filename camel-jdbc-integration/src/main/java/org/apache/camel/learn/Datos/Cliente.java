package org.apache.camel.learn.Datos;

import java.util.ArrayList;

public class Cliente {
    private String ID;
    private String LIMIT_BAL;
    private String SEX;
    private String EDUCATION;
    private String MARRIAGE;
    private String AGE;
    private String DPNMONTH;
    private ArrayList<Facturado> BILL;
    private ArrayList<Pago> PAYMENT;
    

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getLIMIT_BAL() {
        return LIMIT_BAL;
    }

    public void setLIMIT_BAL(String lIMIT_BAL) {
        LIMIT_BAL = lIMIT_BAL;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String sEX) {
        SEX = sEX;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String eDUCATION) {
        EDUCATION = eDUCATION;
    }

    public String getMARRIAGE() {
        return MARRIAGE;
    }

    public void setMARRIAGE(String mARRIAGE) {
        MARRIAGE = mARRIAGE;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String aGE) {
        AGE = aGE;
    }

    public String getDPNMONTH() {
        return DPNMONTH;
    }

    public void setDPNMONTH(String dPNMONTH) {
        DPNMONTH = dPNMONTH;
    }

    public ArrayList<Facturado> getBILL() {
        return BILL;
    }

    public void setBILL(ArrayList<Facturado> bILL) {
        BILL = bILL;
    }

    public ArrayList<Pago> getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(ArrayList<Pago> pAYMENT) {
        PAYMENT = pAYMENT;
    }



}
