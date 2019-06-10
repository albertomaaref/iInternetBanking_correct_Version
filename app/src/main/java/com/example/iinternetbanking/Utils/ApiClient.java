package com.example.iinternetbanking.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static final String BASE_URL="http://www.atb.tn/";
    public static Retrofit retrofit = null;
    public static Retrofit getApiClient(){

        if(retrofit==null){
            retrofit =new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}