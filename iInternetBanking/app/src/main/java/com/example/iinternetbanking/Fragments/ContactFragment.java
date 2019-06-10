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
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // declaration d'un instance du base de donnes firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // creation d'un child from database with name reclamations
        myRef = database.getReference("Reclamations");
        // declaration d'un instance du authentification firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // prendre un instance d'utilisateur courrrant
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        binding.nomClientTxt.setText(CurrentFullUser.getNom()+" "+CurrentFullUser.getPrenom());
        binding.demandeReclamationContainer.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demande_reclamation_container:
                SendReclamation();
                break;
        }
    }

    private void SendReclamation() {
        if (binding.reclamationEditext.getText().toString().contentEquals("")) {
            return;
        }
        binding.demandeReclamationTxt.setVisibility(View.INVISIBLE);
        binding.aviReclamation.setVisibility(View.VISIBLE);
        binding.demandeReclamationContainer.setEnabled(false);
        Reclamation reclamation = new Reclamation(UUID.randomUUID().toString(), CurrentFullUser, binding.reclamationEditext.getText().toString());
        myRef.child(CurrentFullUser.getId()).child(reclamation.getId()).setValue(reclamation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                binding.demandeReclamationContainer.setEnabled(true);
                binding.demandeReclamationTxt.setVisibility(View.VISIBLE);
                binding.aviReclamation.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Reclamation envoye avec succes", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.demandeReclamationTxt.setVisibility(View.VISIBLE);
                binding.aviReclamation.setVisibility(View.INVISIBLE);
                binding.demandeReclamationContainer.setEnabled(true);
                Toast.makeText(getContext(), "Reclamation n'est pas envoye, verifier votre conexion!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
