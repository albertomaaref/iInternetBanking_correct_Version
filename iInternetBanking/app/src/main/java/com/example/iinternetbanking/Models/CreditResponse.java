package com.example.iinternetbanking.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditResponse {

    private Double pmt;


    public CreditResponse() {
    }

    public CreditResponse(Double pmt, List<Double> amtTable) {
        super();
        this.pmt = pmt;

    }

    public Double getPmt() {
        return pmt;
    }

    public void setPmt(Double pmt) {
        this.pmt = pmt;
    }

}