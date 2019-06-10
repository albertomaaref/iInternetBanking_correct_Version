package com.example.iinternetbanking.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iinternetbanking.Models.Transactions;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.ItemTransactionBinding;


import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private Context context;

    public TransactionAdapter(ArrayList<Transactions> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTransactionBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_transaction, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.bindingData(transactions.get(i));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemTransactionBinding binding;

        public MyViewHolder(@NonNull ItemTransactionBinding itemView) {
            super(itemView.getRoot());
            binding =itemView;
        }

        public void bindingData(Transactions transaction) {
            binding.typeTransactionTxt.setText(transaction.getType_transaction().toString());
            binding.dateTransactionTxt.setText(transaction.getDate_transaction().toString());
            binding.montantTransactionTxt.setText(transaction.getMontant().toString());
        }
    }
}
