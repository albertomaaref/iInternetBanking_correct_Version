package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.iinternetbanking.Models.oppositionChequier;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentOppositionChequierBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class OppositionChequierFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private FragmentOppositionChequierBinding binding;
    private FirebaseDatabase database;
    private String motif = "perte";

    public OppositionChequierFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_opposition_chequier, container, false);
        binding.oppoChequierContainer.setOnClickListener(this);
        binding.groupOppositionChequier.setOnCheckedChangeListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oppo_chequier_container:
                saveOppoChequier();
                break;
        }
    }

    private void saveOppoChequier() {
        if (binding.numeroChequier.getText().toString().contentEquals("")) {
            return;
        }
        binding.oppoChequierContainer.setEnabled(false);
        binding.aviOppositionChequier.setVisibility(View.VISIBLE);
        binding.oppoChequierTxt.setVisibility(View.INVISIBLE);
        oppositionChequier oppositionChequier = new oppositionChequier(UUID.randomUUID().toString(), binding.numeroChequier.getText().toString(), "En Attente", motif, CurrentFullUser);
        database.getReference("OppositionChequier").child(CurrentFullUser.getId()).child(oppositionChequier.getId().toString()).setValue(oppositionChequier).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                binding.oppoChequierContainer.setEnabled(true);
                binding.aviOppositionChequier.setVisibility(View.INVISIBLE);
                binding.oppoChequierTxt.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "votre demande d'opposition chequier envoye avec succes!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                binding.oppoChequierContainer.setEnabled(true);
                binding.aviOppositionChequier.setVisibility(View.INVISIBLE);
                binding.oppoChequierTxt.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "demande n'est pas envoye, verifier votre conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.perte_oppo:
                motif = "perte";
                break;
            case R.id.vol_oppo:
                motif = "vol";
                break;
        }
    }
}
