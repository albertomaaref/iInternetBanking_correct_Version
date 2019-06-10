package com.example.iinternetbanking.Fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iinternetbanking.Activities.MainActivity;
import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.User;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private FragmentSignInBinding binding;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRefUser;
    private DatabaseReference myRefCompte;
    private DatabaseReference myRefCarte;

    private ArrayList<Compte> comptes = new ArrayList<>();
    private ArrayList<Cartes> cartes = new ArrayList<>();

    public SignInFragment() {
    }


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View v = binding.getRoot();
        binding.logInContainer.setOnClickListener(this);
        binding.passwordForget.setOnClickListener(this);
        binding.signupLinkTxt.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in_container:
                SignInThrowFirebase();
                break;
            case R.id.password_forget:
                ForgetPassword();
                break;
            case R.id.signup_link_txt:
                moveToFragment(new SignUpFragment());
                break;
        }
    }

    private void SignInThrowFirebase() {
        if (binding.usernameEdittext.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), getString(R.string.mail_not_fill), Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.passwordEdittext.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), getString(R.string.password_not_fill), Toast.LENGTH_SHORT).show();

            return;
        }
        binding.logInTxt.setVisibility(View.INVISIBLE);
        binding.aviLogin.setVisibility(View.VISIBLE);
        binding.logInContainer.setEnabled(false);
        String mail = binding.usernameEdittext.getText().toString();
        String password = binding.passwordEdittext.getText().toString();

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getCurrentFullUser(mAuth.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            binding.logInContainer.setEnabled(true);
                            binding.logInTxt.setVisibility(View.VISIBLE);
                            binding.aviLogin.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void ForgetPassword() {
        if (binding.usernameEdittext.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), getString(R.string.mail_not_fill), Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(binding.usernameEdittext.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), getString(R.string.forget_password_succes), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_login_container, fragment).addToBackStack(null).commit();
    }

    private void getCurrentFullUser(FirebaseUser currentUser){
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
        binding.aviLogin.setVisibility(View.INVISIBLE);
        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("currentfulluser",maincurrentUser);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.alpha, R.anim.slide_down);
        getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
