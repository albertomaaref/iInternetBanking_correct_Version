package com.example.iinternetbanking.Activities;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.iinternetbanking.Fragments.MainFragment;
import com.example.iinternetbanking.Fragments.SignInFragment;
import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.User;
import com.example.iinternetbanking.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static User CurrentFullUser = null;
    public static ArrayList<Compte> CurrentCompte = new ArrayList<>();
    public static ArrayList<Cartes> CurrentCarte = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getParcelableExtra("currentfulluser") != null){
             CurrentFullUser = getIntent().getParcelableExtra("currentfulluser");
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new MainFragment()).commit();
    }
}
