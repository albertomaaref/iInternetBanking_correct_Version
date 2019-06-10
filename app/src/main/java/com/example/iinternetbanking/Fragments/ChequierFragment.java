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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.demandeChequier;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentChequierBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentCompte;
import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class ChequierFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentChequierBinding binding;
    private String nbrPage = "20";
    private String numCompte = "";
    private DatabaseReference myRef;
    private ArrayList<String> numComptes = new ArrayList<>();
    private ArrayAdapter<String> adapterComptes;
    private String type = "barre";

    public ChequierFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("demandeChequiers");
        database.getReference("Comptes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                numComptes.add(dataSnapshot.getValue(Compte.class).getNum_compte());
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chequier, container, false);
        adapterComptes = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, numComptes);
        adapterComptes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinChequier.setAdapter(adapterComptes);
        binding.spinChequier.setOnItemSelectedListener(this);
        binding.demandeChequierContainer.setOnClickListener(this);
        binding.groupChequier.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.twenty : nbrPage = "20";
                    break;
                    case R.id.fifty : nbrPage = "50";
                    break;
                }
            }
        });
        binding.groupChequierFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.barre : type = "barré";
                    break;
                    case R.id.non_barre : type = " non barré";
                    break;
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.demande_chequier_container:
                SaveDemande();
            break;
        }
    }

    private void SaveDemande() {
        if (numCompte.contentEquals("")){
            return;
        }

        binding.aviChequier.setVisibility(View.VISIBLE);
        binding.demandeChequierTxt.setVisibility(View.INVISIBLE);
        binding.demandeChequierContainer.setEnabled(false);
        demandeChequier demandeChequier = new demandeChequier(UUID.randomUUID().toString(),numCompte, type, nbrPage, Calendar.getInstance().getTime(),null,CurrentFullUser);
        myRef.child(CurrentFullUser.getId().toString()).child(demandeChequier.getId()).setValue(demandeChequier).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                binding.aviChequier.setVisibility(View.INVISIBLE);
                binding.demandeChequierTxt.setVisibility(View.VISIBLE);
                binding.demandeChequierContainer.setEnabled(true);
                Toast.makeText(getContext(), "demande envoyée avec succée!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.aviChequier.setVisibility(View.INVISIBLE);
                binding.demandeChequierTxt.setVisibility(View.VISIBLE);
                binding.demandeChequierContainer.setEnabled(true);
                Toast.makeText(getContext(), "demande n'est pas envoyée, vérifiez votre connexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numCompte = numComptes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
