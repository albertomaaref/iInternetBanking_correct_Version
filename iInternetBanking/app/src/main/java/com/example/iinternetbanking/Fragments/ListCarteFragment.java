package com.example.iinternetbanking.Fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iinternetbanking.Adapters.CarteAdapter;
import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentListCarteBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class ListCarteFragment extends Fragment {

    private FragmentListCarteBinding binding;
    private ArrayList<Cartes> cartes = new ArrayList<>();
    private CarteAdapter carteAdapter;
    private FirebaseDatabase database;

    public ListCarteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        database.getReference("Cartes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cartes carte = dataSnapshot.getValue(Cartes.class);
                cartes.add(carte);
                carteAdapter.notifyDataSetChanged();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_carte, container, false);
        carteAdapter = new CarteAdapter(cartes,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerviewCartes.setLayoutManager(layoutManager);
        binding.recyclerviewCartes.setAdapter(carteAdapter);
        return binding.getRoot();
    }

}
