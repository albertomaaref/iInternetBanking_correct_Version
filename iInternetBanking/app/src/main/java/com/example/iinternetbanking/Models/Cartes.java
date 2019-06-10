package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Cartes implements Parcelable {
    private String num_carte;
    private String type_carte;
    private String nature_carte;
    private String nom_client;
    private String prenom_client;
    private Date date_echeance;
    private String num_compte;
    private User user;

    protected Cartes(Parcel in) {
        num_carte = in.readString();
        type_carte = in.readString();
        nature_carte = in.readString();
        nom_client = in.readString();
        prenom_client = in.readString();
        num_compte = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num_carte);
        dest.writeString(type_carte);
        dest.writeString(nature_carte);
        dest.writeString(nom_client);
        dest.writeString(prenom_client);
        dest.writeString(num_compte);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cartes> CREATOR = new Creator<Cartes>() {
        @Override
        public Cartes createFromParcel(Parcel in) {
            return new Cartes(in);
        }

        @Override
        public Cartes[] newArray(int size) {
            return new Cartes[size];
        }
    };

    public String getNum_carte() {
        return num_carte;
    }

    public void setNum_carte(String num_carte) {
        this.num_carte = num_carte;
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

    public Date getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cartes() {
    }

    public Cartes(String num_carte, String type_carte, String nature_carte, String nom_client, String prenom_client, Date date_echeance, String num_compte, User user) {
        this.num_carte = num_carte;
        this.type_carte = type_carte;
        this.nature_carte = nature_carte;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.date_echeance = date_echeance;
        this.num_compte = num_compte;
        this.user = user;
    }
}
