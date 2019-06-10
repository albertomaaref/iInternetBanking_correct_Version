package com.example.iinternetbanking.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.iinternetbanking.Fragments.ContactFragment;
import com.example.iinternetbanking.Fragments.MainFragment;
import com.example.iinternetbanking.Fragments.SignInFragment;
import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.User;
import com.example.iinternetbanking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRefUser;
    private DatabaseReference myRefCompte;
    private DatabaseReference myRefCarte;

    private ArrayList<Compte> comptes = new ArrayList<>();
    private ArrayList<Cartes> cartes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       getSupportFragmentManager().beginTransaction().add(R.id.fragment_login_container,new ContactFragment()).addToBackStack(null).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            getCurrentFullUser();
        }
    }

    private void getCurrentFullUser(){
        database = FirebaseDatabase.getInstance();
        myRefUser = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        myRefUser.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User MaincurrentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(User maincurrentUser) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("currentfulluser",maincurrentUser);
        startActivity(i);
        overridePendingTransition(R.anim.alpha, R.anim.slide_down);
        finish();
    }

}
