package com.ezesky.likaa.BD_sqlite;

/**
 * Created by ezesky on 01/04/2016.
 */
public class Itineraire {


    private String ID_ITI;
    private String LIB_ITI;
    private String MOYEN_TP_ITI;
    private String TYPE_ITI;
    private String DETAIL_ITI;


    public String getID_ITI() {
        return ID_ITI;
    }

    public void setID_ITI(String ID_ITI) {
        this.ID_ITI = ID_ITI;
    }

    public String getLIB_ITI() {
        return LIB_ITI;
    }

    public void setLIB_ITI(String LIB_ITI) {
        this.LIB_ITI = LIB_ITI;
    }

    public String getMOYEN_TP_ITI() {
        return MOYEN_TP_ITI;
    }

    public void setMOYEN_TP_ITI(String MOYEN_TP_ITI) {
        this.MOYEN_TP_ITI = MOYEN_TP_ITI;
    }

    public String getTYPE_ITI() {
        return TYPE_ITI;
    }

    public void setTYPE_ITI(String TYPE_ITI) {
        this.TYPE_ITI = TYPE_ITI;
    }

    public String getDETAIL_ITI() {
        return DETAIL_ITI;
    }

    public void setDETAIL_ITI(String DETAIL_ITI) {
        this.DETAIL_ITI = DETAIL_ITI;
    }
}
