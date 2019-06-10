package com.example.iinternetbanking.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.User;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {


    private ActivitySplashScreenBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRefUser;
    private DatabaseReference myRefCompte;
    private DatabaseReference myRefCarte;
    private Intent i;
    private ArrayList<Compte> comptes = new ArrayList<>();
    private ArrayList<Cartes> cartes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.ABSOLUTE);
        animation.setDuration(2000);
        binding.splashscreenContainer.setAnimation(animation);

    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            getCurrentFullUser();
        }
        else {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.alpha, R.anim.slide_down);
            finish();
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
                updateUI(MaincurrentUser);
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
