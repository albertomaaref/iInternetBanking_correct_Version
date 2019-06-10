package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class oppositionCarte implements Parcelable {
    private String id;
    private String num_carte;
    private String motif;
    private Date date_demande;
    private Date date_validation;
    private String etat;
    private User user;

    protected oppositionCarte(Parcel in) {
        id = in.readString();
        num_carte = in.readString();
        motif = in.readString();
        etat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(num_carte);
        dest.writeString(motif);
        dest.writeString(etat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<oppositionCarte> CREATOR = new Creator<oppositionCarte>() {
        @Override
        public oppositionCarte createFromParcel(Parcel in) {
            return new oppositionCarte(in);
        }

        @Override
        public oppositionCarte[] newArray(int size) {
            return new oppositionCarte[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum_carte() {
        return num_carte;
    }

    public void setNum_carte(String num_carte) {
        this.num_carte = num_carte;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public Date getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(Date date_validation) {
        this.date_validation = date_validation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Creator<oppositionCarte> getCREATOR() {
        return CREATOR;
    }

    public oppositionCarte() {
    }

    public oppositionCarte(String id, String num_carte, String motif, Date date_demande, Date date_validation, String etat, User user) {
        this.id = id;
        this.num_carte = num_carte;
        this.motif = motif;
        this.date_demande = date_demande;
        this.date_validation = date_validation;
        this.etat = etat;
        this.user = user;
    }
}
