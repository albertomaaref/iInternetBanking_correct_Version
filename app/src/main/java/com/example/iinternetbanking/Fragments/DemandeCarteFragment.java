package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.system.Os;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.demandeCarte;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentDemandeCarteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class DemandeCarteFragment extends Fragment implements View.OnClickListener {


    private FragmentDemandeCarteBinding binding;
    private String[] OS = new String[]{ "Carte technologique ATB","Carte Lella","Carte El Khir","Carte Moussafer Platinum","Carte Mastercard Word","Carte Mastercard","Carte C'Jeune","Carte VISA GOLD","Carte Amex" };
    private FirebaseDatabase database;
    private ArrayList<String> numComptes = new ArrayList<>();
    private ArrayList<Compte> comptes = new ArrayList<>();
    private ArrayAdapter<String> adapterComptes;
    private String numCompte = "";
    private String typeCarte = "";
    private Compte compte;


    public DemandeCarteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        database.getReference("Comptes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                numComptes.add(dataSnapshot.getValue(Compte.class).getNum_compte());
                comptes.add(dataSnapshot.getValue(Compte.class));
                adapterComptes.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_demande_carte, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, OS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spin.setAdapter(adapter);
        typeCarte = OS[0];
        binding.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeCarte = OS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterComptes = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, numComptes);
        adapterComptes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinCarte.setAdapter(adapterComptes);
        binding.spinCarte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numCompte = numComptes.get(position);
                compte = comptes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.demandeCarteContainer.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.demande_carte_container: DemandeCarte();
            break;
        }
    }

    private void DemandeCarte() {
        if (numCompte.contentEquals("")){
            return;
        }
        if (typeCarte.contentEquals("")){
            return;
        }
        binding.demandeCarteContainer.setEnabled(false);
        binding.demandeCarteTxt.setVisibility(View.INVISIBLE);
        binding.aviCarte.setVisibility(View.VISIBLE);
        demandeCarte demandeCarte = new demandeCarte(UUID.randomUUID().toString(),typeCarte,"Default",CurrentFullUser.getNom(),CurrentFullUser.getPrenom(), Calendar.getInstance().getTime(),null,"en Attente",CurrentFullUser);
        database.getReference("demandeCartes").child(CurrentFullUser.getId()).setValue(demandeCarte).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                binding.demandeCarteContainer.setEnabled(true);
                binding.demandeCarteTxt.setVisibility(View.VISIBLE);
                binding.aviCarte.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Demande Carte effectue avec succes!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.demandeCarteContainer.setEnabled(true);
                binding.demandeCarteTxt.setVisibility(View.VISIBLE);
                binding.aviCarte.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Demande Carte n'est pas envoye, verifier votre conexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
