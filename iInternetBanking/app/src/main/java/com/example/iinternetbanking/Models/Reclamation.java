package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reclamation implements Parcelable {
    private String id;
    private User user;
    private String recalamation;

    protected Reclamation(Parcel in) {
        id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        recalamation = in.readString();
    }

    public static final Creator<Reclamation> CREATOR = new Creator<Reclamation>() {
        @Override
        public Reclamation createFromParcel(Parcel in) {
            return new Reclamation(in);
        }

        @Override
        public Reclamation[] newArray(int size) {
            return new Reclamation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(user, flags);
        dest.writeString(recalamation);
    }

    public Reclamation(String id, User user, String recalamation) {
        this.id = id;
        this.user = user;
        this.recalamation = recalamation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecalamation() {
        return recalamation;
    }

    public void setRecalamation(String recalamation) {
        this.recalamation = recalamation;
    }

    public static Creator<Reclamation> getCREATOR() {
        return CREATOR;
    }
}
