package com.example.iinternetbanking.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClientCredit {
    public static final String BASE_URL="http://www.iotafinance.com/";
    public static Retrofit retrofit = null;
    public static Retrofit getApiClient(){

        if(retrofit==null){
            retrofit =new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}