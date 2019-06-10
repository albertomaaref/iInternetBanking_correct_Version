package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.iinternetbanking.Models.CreditInfo;
import com.example.iinternetbanking.Models.CreditResponse;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.Utils.ApiClient;
import com.example.iinternetbanking.Utils.ApiClientCredit;
import com.example.iinternetbanking.Utils.ApiInterface;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.iinternetbanking.Utils.ApiClient.*;
import com.example.iinternetbanking.databinding.FragmentCreditBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {


    private ApiInterface apiInterface;
    private FragmentCreditBinding binding;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> typeCredit = new ArrayList<>();
    private double rate = 9.5;

    public CreditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        typeCredit.add("Credit consommation");
        typeCredit.add("Credit amenagement");
        typeCredit.add("Credit immobilié");
        typeCredit.add("Crédit voiture");
        apiInterface = ApiClientCredit.getApiClient().create(ApiInterface.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      /*binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_credit, container, false);
      binding.mensualiteTxt.setVisibility(View.GONE);
      adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeCredit);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      binding.spinCredit.setAdapter(adapter);
      binding.spinCredit.setOnItemSelectedListener(this);
      binding.similateurCreditContainer.setOnClickListener(this);
      binding.seekBarCredit.setOnSeekBarChangeListener(this);
        binding.durationTxt.setText("Duree (Mois) ="+binding.seekBarCredit.getProgress());
      return binding.getRoot();*/

      View view = inflater.inflate(R.layout.fragment_credit, container, false);
      return view;

    }


    private void CallCredit(double cap, double rate, double fctSelect, double duration, double ppy, double perType) {
        binding.aviCredit.setVisibility(View.VISIBLE);
        binding.similateurCreditTxt.setVisibility(View.GONE);
        Call<CreditResponse> api = apiInterface.CalculCredit(cap, rate, fctSelect, duration, ppy, perType);
        api.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.code() == 200) {
                    binding.mensualiteTxt.setVisibility(View.VISIBLE);
                    binding.similateurCreditTxt.setVisibility(View.VISIBLE);
                    binding.aviCredit.setVisibility(View.GONE);
                    binding.mensualiteTxt.setText("Mensualité du crédit : " + response.body().getPmt().toString());
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.similateurCreditTxt.setVisibility(View.VISIBLE);
                binding.aviCredit.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                rate = 9.5;
                break;
            case 1:
                rate = 8.5;
                break;
            case 2:
                rate = 8.25;
                break;
            case 3:
                rate = 9.25;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.similateur_credit_container:
                CalculCredit();
                break;
        }
    }

    private void CalculCredit() {
        if (binding.capitalCreditEdittext.getText().toString().contentEquals("")) {
            return;
        }
        CallCredit(Double.parseDouble(binding.capitalCreditEdittext.getText().toString()), rate, 1, binding.seekBarCredit.getProgress(), 12, 1);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        binding.durationTxt.setText("Duree (Mois) =" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
