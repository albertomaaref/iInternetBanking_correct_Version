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

import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.oppositionCarte;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentOppositionCarteBinding;
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

import static com.example.iinternetbanking.Activities.MainActivity.CurrentCarte;
import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class OppositionCarteFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private FragmentOppositionCarteBinding binding;
    private DatabaseReference OppositionCarteRef;
    private ArrayAdapter<String> adapterCartes;
    private ArrayList<String> numCartes = new ArrayList<>();
    private String numCarte = "";
    private String motif = "perte";

    public OppositionCarteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        OppositionCarteRef = database.getReference("OppositionCartes");
        database.getReference("Cartes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                numCartes.add(dataSnapshot.getValue(Cartes.class).getNum_carte());
                adapterCartes.notifyDataSetChanged();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_opposition_carte, container, false);
        adapterCartes = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, numCartes);
        adapterCartes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinOppositionCarte.setAdapter(adapterCartes);
        binding.spinOppositionCarte.setOnItemSelectedListener(this);
        binding.oppositionCarteContainer.setOnClickListener(this);
        binding.groupOppositionCarte.setOnCheckedChangeListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opposition_carte_container:
                SaveOppositionCarte();
                break;
        }
    }

    private void SaveOppositionCarte() {
        if (numCarte.contentEquals("")) {
            return;
        }
        binding.oppositionCarteTxt.setVisibility(View.INVISIBLE);
        binding.aviOppositionCarte.setVisibility(View.VISIBLE);
        binding.oppositionCarteContainer.setEnabled(false);
        oppositionCarte oppositionCarte = new oppositionCarte(UUID.randomUUID().toString(), numCarte, motif, Calendar.getInstance().getTime(), null, "En Attente", CurrentFullUser);
        OppositionCarteRef.child(CurrentFullUser.getId()).child(oppositionCarte.getId()).setValue(oppositionCarte).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Opposition Carte envoye avec succes", Toast.LENGTH_SHORT).show();
                binding.oppositionCarteContainer.setEnabled(true);
                binding.oppositionCarteTxt.setVisibility(View.VISIBLE);
                binding.aviOppositionCarte.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Opposition Carte n'est pas envoye, verifier votre conexion!", Toast.LENGTH_SHORT).show();
                binding.oppositionCarteContainer.setEnabled(true);
                binding.oppositionCarteTxt.setVisibility(View.VISIBLE);
                binding.aviOppositionCarte.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numCarte = numCartes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.vol){
            motif = "vol";
            return;
        }
        if (checkedId == R.id.perte){
            motif = "perte";
            return;
        }
    }
}
