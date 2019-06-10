package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.iinternetbanking.Adapters.TransactionAdapter;
import com.example.iinternetbanking.Models.Transactions;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentTransactionsBinding;
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

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference TransactionsRef;
    private TransactionAdapter transactionAdapter;

    public TransactionsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactions.clear();
        database = FirebaseDatabase.getInstance();
        TransactionsRef = database.getReference("Transactions");


        TransactionsRef.child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                transactions.add(dataSnapshot.getValue(Transactions.class));
                transactionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = transactions.indexOf(dataSnapshot.getValue(Transactions.class));
                transactions.set(index,dataSnapshot.getValue(Transactions.class));
                transactionAdapter.notifyItemChanged(index);
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transactions, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        transactionAdapter = new TransactionAdapter(transactions,getContext());
        binding.recyclerviewTransactions.setLayoutManager(layoutManager);
        binding.recyclerviewTransactions.setAdapter(transactionAdapter);
        return binding.getRoot();
    }

}
