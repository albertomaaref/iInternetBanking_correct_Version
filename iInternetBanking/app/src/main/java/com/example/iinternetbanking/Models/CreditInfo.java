package com.example.iinternetbanking.Models;

public class CreditInfo {

    private int cap;
    private double rate;
    private int fctSelect;
    private int duration;
    private int ppy;
    private int perType;

    public CreditInfo() {
    }

    public CreditInfo(int cap, double rate, int fctSelect, int duration, int ppy, int perType) {
        this.cap = cap;
        this.rate = rate;
        this.fctSelect = fctSelect;
        this.duration = duration;
        this.ppy = ppy;
        this.perType = perType;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getFctSelect() {
        return fctSelect;
    }

    public void setFctSelect(int fctSelect) {
        this.fctSelect = fctSelect;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPpy() {
        return ppy;
    }

    public void setPpy(int ppy) {
        this.ppy = ppy;
    }

    public int getPerType() {
        return perType;
    }

    public void setPerType(int perType) {
        this.perType = perType;
    }
}
