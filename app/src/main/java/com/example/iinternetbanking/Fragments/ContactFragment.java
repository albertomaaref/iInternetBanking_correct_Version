package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iinternetbanking.Models.Reclamation;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentContactBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class ContactFragment extends Fragment implements View.OnClickListener {
    private FragmentContactBinding binding;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        binding.entrer.setOnClickListener(this);
        binding.simulateur.setOnClickListener(this);
        binding.dev.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entrer : GoToFragment (new SignInFragment());
                break;
            case R.id.simulateur : GoToFragment(new CreditFragment());
                break;
            case R.id.dev : GoToFragment(new DevisesFragment());
                break;
        }
    }

    private void GoToFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_login_container,fragment).commit();
    }
}