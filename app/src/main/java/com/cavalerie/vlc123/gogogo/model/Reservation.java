package com.cavalerie.vlc123.gogogo.model;

public class Reservation {
    private String txtdepart;
    private String txtarrive;
    private String txthdepart;
    private String txtharrive;
    private String datevoyage;
    private String txtplace;
    private String txtprice;

    public Reservation(String txtdepart, String txtarrive, String txthdepart, String txtharrive, String datevoyage, String txtprice) {
        this.txtdepart = txtdepart;
        this.txtarrive = txtarrive;
        this.txthdepart = txthdepart;
        this.txtharrive = txtharrive;
        this.datevoyage = datevoyage;
        this.txtprice = txtprice;
    }

    public Reservation(String datevoyage, String txtdepart, String txtarrive, String txthdepart, String txtharrive, String txtplace, String txtprice) {
        this.datevoyage = datevoyage;
        this.txtdepart = txtdepart;
        this.txtarrive = txtarrive;
        this.txthdepart = txthdepart;
        this.txtharrive = txtharrive;
        this.txtplace = txtplace;
        this.txtprice = txtprice;
    }

    public String getTxtdepart() {
        return txtdepart;
    }

    public String getTxtarrive() {
        return txtarrive;
    }

    public String getTxthdepart() {
        return txthdepart;
    }

    public String getTxtharrive() {
        return txtharrive;
    }

    public String getDatevoyage() {
        return datevoyage;
    }

    public String getTxtplace() {
        return txtplace;
    }

    public String getTxtprice() {
        return txtprice;
    }
}
