package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class demandeChequier implements Parcelable {
    private String id;
    private String nbre_page;
    private String num_compte;
    private String type;
    private Date date_demande;
    private Date date_validation;
    private User user;

    public demandeChequier() {
    }

    public demandeChequier(String id, String num_compte, String type, String nbre_page, Date date_demande, Date date_validation, User user) {
        this.id = id;
        this.nbre_page = nbre_page;
        this.num_compte = num_compte;
        this.type = type;
        this.date_demande = date_demande;
        this.date_validation = date_validation;
        this.user = user;
    }

    protected demandeChequier(Parcel in) {
        id = in.readString();
        nbre_page = in.readString();
        num_compte = in.readString();
        type = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nbre_page);
        dest.writeString(num_compte);
        dest.writeString(type);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<demandeChequier> CREATOR = new Creator<demandeChequier>() {
        @Override
        public demandeChequier createFromParcel(Parcel in) {
            return new demandeChequier(in);
        }

        @Override
        public demandeChequier[] newArray(int size) {
            return new demandeChequier[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNbre_page() {
        return nbre_page;
    }

    public void setNbre_page(String nbre_page) {
        this.nbre_page = nbre_page;
    }

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

