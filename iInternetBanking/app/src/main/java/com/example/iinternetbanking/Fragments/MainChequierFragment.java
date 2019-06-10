package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentMainChequierBinding;

public class MainChequierFragment extends Fragment implements View.OnClickListener {


    private FragmentMainChequierBinding binding;

    public MainChequierFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chequier, container, false);
        binding.dmd.setOnClickListener(this);
        binding.opposition.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dmd:
                GotoFragment(new ChequierFragment());
                break;
            case R.id.opposition:
                GotoFragment(new OppositionChequierFragment());
                break;
        }
    }

    private void GotoFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
