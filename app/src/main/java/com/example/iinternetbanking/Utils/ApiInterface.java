package com.example.iinternetbanking.Utils;

import com.example.iinternetbanking.Models.CreditResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @Headers("Content-Type: text/html")
    @GET("calc_convertisseur.php")
    Call<String> CalculDevise(@Query("montant_saisi") String montantSaisi, @Query("devise_depart") String deviseDepart, @Query("devise_sortie") String deviseSortie);

    @FormUrlEncoded
    @POST("ajax/clcCredit.php")
    Call<CreditResponse> CalculCredit(@Field("cap") double cap,@Field("rate") double rate,@Field("fctSelect") double fctSelect,@Field("duration") double duration,@Field("ppy") double ppy,@Field("perType") double perType);
}
