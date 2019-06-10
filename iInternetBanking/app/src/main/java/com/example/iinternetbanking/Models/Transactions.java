package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Transactions implements Parcelable {
    private String id;
    private String type_transaction;
    private Date date_transaction;
    private String montant;
    private Compte compte;

    protected Transactions(Parcel in) {
        id = in.readString();
        type_transaction = in.readString();
        montant = in.readString();
        compte = in.readParcelable(Compte.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type_transaction);
        dest.writeString(montant);
        dest.writeParcelable(compte, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transactions> CREATOR = new Creator<Transactions>() {
        @Override
        public Transactions createFromParcel(Parcel in) {
            return new Transactions(in);
        }

        @Override
        public Transactions[] newArray(int size) {
            return new Transactions[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_transaction() {
        return type_transaction;
    }

    public void setType_transaction(String type_transaction) {
        this.type_transaction = type_transaction;
    }

    public Date getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(Date date_transaction) {
        this.date_transaction = date_transaction;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public static Creator<Transactions> getCREATOR() {
        return CREATOR;
    }

    public Transactions() {
    }

    public Transactions(String id, String type_transaction, Date date_transaction, String montant, Compte compte) {
        this.id = id;
        this.type_transaction = type_transaction;
        this.date_transaction = date_transaction;
        this.montant = montant;
        this.compte = compte;
    }
}
