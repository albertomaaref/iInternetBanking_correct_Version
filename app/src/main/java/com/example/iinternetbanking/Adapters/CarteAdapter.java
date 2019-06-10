package com.example.iinternetbanking.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Transactions;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.ItemCarteBinding;
import com.example.iinternetbanking.databinding.ItemTransactionBinding;

import java.util.ArrayList;

public class CarteAdapter extends RecyclerView.Adapter<CarteAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Cartes> cartes = new ArrayList<>();
    private Context context;

    public CarteAdapter(ArrayList<Cartes> cartes, Context context) {
        this.cartes = cartes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCarteBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_carte, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.bindingData(cartes.get(i));
    }

    @Override
    public int getItemCount() {
        return cartes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemCarteBinding binding;

        public MyViewHolder(@NonNull ItemCarteBinding itemView) {
            super(itemView.getRoot());
            binding =itemView;
        }

        public void bindingData(Cartes cartes) {
            binding.typeCarteTxt.setText(cartes.getType_carte().toString());
            binding.dateCarteTxt.setText(cartes.getDate_echeance().toString());
            binding.numeroCarteTxt.setText(cartes.getNum_carte().toString());
        }
    }
}
