package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.iinternetbanking.R;
import com.example.iinternetbanking.Utils.ApiClient;
import com.example.iinternetbanking.Utils.ApiInterface;
import com.example.iinternetbanking.databinding.FragmentDevisesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevisesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentDevisesBinding binding;
    private ApiInterface apiInterface;
    private Call<String> call;
    private String orCurrency;
    private String exCurrency;


    public DevisesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      binding = DataBindingUtil.inflate(inflater,R.layout.fragment_devises, container, false);
      binding.demandeDevisesContainer.setOnClickListener(this);
      binding.spinner1.setOnItemSelectedListener(this);
      binding.spinner2.setOnItemSelectedListener(this);
        return binding.getRoot();
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.demande_devises_container : ConvertDevises();
            break;
        }
    }

    private void ConvertDevises() {
        if (binding.deviseEdittext.getText().toString().contentEquals("")){
            return;
        }
        binding.convertirTxt.setVisibility(View.INVISIBLE);
        binding.aviDevises.setVisibility(View.VISIBLE);
        binding.demandeDevisesContainer.setEnabled(false);
      call =  apiInterface.CalculDevise(binding.deviseEdittext.getText().toString(),orCurrency,exCurrency);
      call.enqueue(new Callback<String>() {
          @Override
          public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.code() == 200){
                    binding.deviseResultEdittext.setText(response.body().toString());
                }
              binding.convertirTxt.setVisibility(View.VISIBLE);
              binding.aviDevises.setVisibility(View.INVISIBLE);
              binding.demandeDevisesContainer.setEnabled(true);
          }

          @Override
          public void onFailure(retrofit2.Call<String> call, Throwable t) {
              binding.convertirTxt.setVisibility(View.VISIBLE);
              binding.aviDevises.setVisibility(View.INVISIBLE);
              binding.demandeDevisesContainer.setEnabled(true);
          }
      });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner1){
            Log.d("spinner1", "onItemClick: "+parent.getItemAtPosition(position).toString());
            SubStringCurrency(parent.getItemAtPosition(position).toString(),1);
        } else if(parent.getId() == R.id.spinner2)
        {
            Log.d("spinner2", "onItemClick: "+parent.getItemAtPosition(position).toString());
            SubStringCurrency(parent.getItemAtPosition(position).toString(),2);
        }

    }

    private void SubStringCurrency(String fullCurrency, int spinner) {
        int index = fullCurrency.indexOf("-");
        if (spinner==1){
            orCurrency = fullCurrency.substring(0,index-1);
        } else {
            exCurrency = fullCurrency.substring(0,index-1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
