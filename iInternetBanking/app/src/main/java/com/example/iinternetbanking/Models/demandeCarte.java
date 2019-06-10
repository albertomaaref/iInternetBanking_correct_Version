package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class demandeCarte implements Parcelable {
    private String id;
    private String type_carte;
    private String nature_carte;
    private String nom_client;
    private String prenom_client;
    private Date date_demande;
    private Date date_validation;
    private String etat;
    private User user;

    protected demandeCarte(Parcel in) {
        id = in.readString();
        type_carte = in.readString();
        nature_carte = in.readString();
        nom_client = in.readString();
        prenom_client = in.readString();
        etat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type_carte);
        dest.writeString(nature_carte);
        dest.writeString(nom_client);
        dest.writeString(prenom_client);
        dest.writeString(etat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<demandeCarte> CREATOR = new Creator<demandeCarte>() {
        @Override
        public demandeCarte createFromParcel(Parcel in) {
            return new demandeCarte(in);
        }

        @Override
        public demandeCarte[] newArray(int size) {
            return new demandeCarte[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_carte() {
        return type_carte;
    }

    public void setType_carte(String type_carte) {
        this.type_carte = type_carte;
    }

    public String getNature_carte() {
        return nature_carte;
    }

    public void setNature_carte(String nature_carte) {
        this.nature_carte = nature_carte;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
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

    public static Creator<demandeCarte> getCREATOR() {
        return CREATOR;
    }

    public demandeCarte() {
    }

    public demandeCarte(String id, String type_carte, String nature_carte, String nom_client, String prenom_client, Date date_demande, Date date_validation, String etat, User user) {
        this.id = id;
        this.type_carte = type_carte;
        this.nature_carte = nature_carte;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.date_demande = date_demande;
        this.date_validation = date_validation;
        this.etat = etat;
        this.user = user;
    }
}
