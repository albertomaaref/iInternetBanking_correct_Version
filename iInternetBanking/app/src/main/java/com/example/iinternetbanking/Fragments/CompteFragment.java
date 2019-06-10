package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentCompteBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class CompteFragment extends Fragment {

    private FragmentCompteBinding binding;
    private FirebaseDatabase database;
    private ArrayList<String> numComptes = new ArrayList<>();
    private ArrayList<Compte> comptes = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public CompteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        database.getReference("Comptes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                numComptes.add(dataSnapshot.getValue(Compte.class).getNum_compte());
                comptes.add(dataSnapshot.getValue(Compte.class));
                adapter.notifyDataSetChanged();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_compte, container, false);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, numComptes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinCompte.setAdapter(adapter);
        binding.spinCompte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDetailsCompte(comptes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }

    private void getDetailsCompte(Compte compte) {
        binding.compteDetailsContainer.setVisibility(View.VISIBLE);
        binding.nomTxt.setText(compte.getNom_compte());
        binding.numTxt.setText(compte.getNum_compte());
        binding.soldeTxt.setText(compte.getSolde());
        binding.soldeDevisesTxt.setText(compte.getDevises_compte());
    }

}
