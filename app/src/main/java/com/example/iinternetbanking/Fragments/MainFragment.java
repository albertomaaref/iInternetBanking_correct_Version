package com.example.iinternetbanking.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iinternetbanking.Fragments.ContactFragment;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding binding;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FirebaseAuth mAuth;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false);
        binding.carte.setOnClickListener(this);
        binding.cheque.setOnClickListener(this);
        binding.compte.setOnClickListener(this);
        binding.historique.setOnClickListener(this);
        binding.virement.setOnClickListener(this);
        binding.deconexion.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.carte : GotoFragment(new MainCarteFragment());
            break;
            case R.id.cheque : GotoFragment(new MainChequierFragment());
            break;
            case R.id.compte : GotoFragment(new CompteFragment());
            break;
            case R.id.historique : GotoFragment(new TransactionsFragment());
            break;
            case R.id.virement : GotoFragment(new VirementsFragment());
            break;
            case R.id.deconexion : mAuth.signOut();
            getActivity().finish();
            break;
        }
    }

    private void GotoFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }
}
