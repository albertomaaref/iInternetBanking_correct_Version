package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Compte implements Parcelable {
    private String num_compte;
    private String nom_compte;
    private String RIB;
    private TypeCompteEnum type_compte;
    private String solde;
    private String devises_compte;
    private User user;


    protected Compte(Parcel in) {
        num_compte = in.readString();
        nom_compte = in.readString();
        RIB = in.readString();
        solde = in.readString();
        devises_compte = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num_compte);
        dest.writeString(nom_compte);
        dest.writeString(RIB);
        dest.writeString(solde);
        dest.writeString(devises_compte);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Compte> CREATOR = new Creator<Compte>() {
        @Override
        public Compte createFromParcel(Parcel in) {
            return new Compte(in);
        }

        @Override
        public Compte[] newArray(int size) {
            return new Compte[size];
        }
    };

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }

    public String getNom_compte() {
        return nom_compte;
    }

    public void setNom_compte(String nom_compte) {
        this.nom_compte = nom_compte;
    }

    public String getRIB() {
        return RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public TypeCompteEnum getType_compte() {
        return type_compte;
    }

    public void setType_compte(TypeCompteEnum type_compte) {
        this.type_compte = type_compte;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getDevises_compte() {
        return devises_compte;
    }

    public void setDevises_compte(String devises_compte) {
        this.devises_compte = devises_compte;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Creator<Compte> getCREATOR() {
        return CREATOR;
    }

    public Compte() {
    }

    public Compte(String num_compte, String nom_compte, String RIB, TypeCompteEnum type_compte, String solde, String devises_compte, User user) {
        this.num_compte = num_compte;
        this.nom_compte = nom_compte;
        this.RIB = RIB;
        this.type_compte = type_compte;
        this.solde = solde;
        this.devises_compte = devises_compte;
        this.user = user;
    }
}
