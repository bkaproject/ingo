package com.cavalerie.vlc123.gogogo.model;

import android.net.Uri;

import java.net.URI;

public class Publication {
    private String txtuser;
    private String txthour;
    private String txttext;
    private String txtlike;
    private String imgimage;

    public Publication(String txtuser, String txthour, String txttext, String txtlike, String imgimage) {
        this.txtuser = txtuser;
        this.txthour = txthour;
        this.txttext = txttext;
        this.txtlike = txtlike;
        this.imgimage = imgimage;
    }

    public String getTxttext() {
        return txttext;
    }

    public void setTxttext(String txttext) {
        this.txttext = txttext;
    }

    public String getImgimage() {
        return imgimage;
    }

    public void setImgimage() {
        this.imgimage = imgimage;
    }

    public String getTxthour() {
        return txthour;
    }

    public void setTxthour(String txthour) {
        this.txthour = txthour;
    }

    public String getTxtlike() {
        return txtlike;
    }

    public void setTxtlike(String txtlike) {
        this.txtlike = txtlike;
    }

    public String getTxtuser() {
        return txtuser;
    }

}
