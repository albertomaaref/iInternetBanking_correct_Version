package com.example.iinternetbanking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class demandeVirement implements Parcelable {
    private String num_compte_d;
    private String num_compte_b;
    private String montant_virement;
    private Date date_virement;
    private Date date_demande;
    private Date date_validation;

    protected demandeVirement(Parcel in) {
        num_compte_d = in.readString();
        num_compte_b = in.readString();
        montant_virement = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num_compte_d);
        dest.writeString(num_compte_b);
        dest.writeString(montant_virement);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<demandeVirement> CREATOR = new Creator<demandeVirement>() {
        @Override
        public demandeVirement createFromParcel(Parcel in) {
            return new demandeVirement(in);
        }

        @Override
        public demandeVirement[] newArray(int size) {
            return new demandeVirement[size];
        }
    };

    public String getNum_compte_d() {
        return num_compte_d;
    }

    public void setNum_compte_d(String num_compte_d) {
        this.num_compte_d = num_compte_d;
    }

    public String getNum_compte_b() {
        return num_compte_b;
    }

    public void setNum_compte_b(String num_compte_b) {
        this.num_compte_b = num_compte_b;
    }

    public String getMontant_virement() {
        return montant_virement;
    }

    public void setMontant_virement(String montant_virement) {
        this.montant_virement = montant_virement;
    }

    public Date getDate_virement() {
        return date_virement;
    }

    public void setDate_virement(Date date_virement) {
        this.date_virement = date_virement;
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

    public static Creator<demandeVirement> getCREATOR() {
        return CREATOR;
    }

    public demandeVirement() {
    }

    public demandeVirement(String num_compte_d, String num_compte_b, String montant_virement, Date date_virement, Date date_demande, Date date_validation) {
        this.num_compte_d = num_compte_d;
        this.num_compte_b = num_compte_b;
        this.montant_virement = montant_virement;
        this.date_virement = date_virement;
        this.date_demande = date_demande;
        this.date_validation = date_validation;
    }
}
