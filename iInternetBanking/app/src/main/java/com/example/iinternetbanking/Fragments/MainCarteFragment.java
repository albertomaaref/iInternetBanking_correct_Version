package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentMainCarteBinding;

public class MainCarteFragment extends Fragment implements View.OnClickListener {


    private FragmentMainCarteBinding binding;

    public MainCarteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_carte, container, false);
        binding.demandecarte.setOnClickListener(this);
        binding.listcarte.setOnClickListener(this);
        binding.opposcarte.setOnClickListener(this);
        binding.retour.setOnClickListener(this);
    return binding.getRoot(); }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.demandecarte : GoToFragment(new DemandeCarteFragment());
            break;
            case R.id.opposcarte : GoToFragment(new OppositionCarteFragment());
            break;
            case R.id.listcarte : GoToFragment(new ListCarteFragment());
            break;
            case R.id.retour : getFragmentManager().popBackStack();
            break;
        }
    }

    private void GoToFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }
}
