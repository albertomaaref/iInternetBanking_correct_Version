package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class oppositionChequier  implements Parcelable {

    private String id;
    private String num_chequier;
    private String etat;
    private String motif;
    private User user;

    public oppositionChequier() {
    }

    public oppositionChequier(String id, String num_chequier, String etat, String motif, User user) {
        this.id = id;
        this.num_chequier = num_chequier;
        this.etat = etat;
        this.motif = motif;
        this.user = user;
    }

    protected oppositionChequier(Parcel in) {
        id = in.readString();
        num_chequier = in.readString();
        etat = in.readString();
        motif = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<oppositionChequier> CREATOR = new Creator<oppositionChequier>() {
        @Override
        public oppositionChequier createFromParcel(Parcel in) {
            return new oppositionChequier(in);
        }

        @Override
        public oppositionChequier[] newArray(int size) {
            return new oppositionChequier[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum_chequier() {
        return num_chequier;
    }

    public void setNum_chequier(String num_chequier) {
        this.num_chequier = num_chequier;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(num_chequier);
        dest.writeString(etat);
        dest.writeString(motif);
        dest.writeParcelable(user, flags);
    }
}
