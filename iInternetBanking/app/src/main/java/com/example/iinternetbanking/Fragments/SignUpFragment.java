package com.example.iinternetbanking.Fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iinternetbanking.Activities.MainActivity;
import com.example.iinternetbanking.Models.Cartes;
import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.TypeCompteEnum;
import com.example.iinternetbanking.Models.User;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private FragmentSignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference CompteRef;
    private DatabaseReference CarteRef;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        CompteRef = database.getReference("Comptes");
        CarteRef = database.getReference("Cartes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        View v = binding.getRoot();
        binding.signUpContainer.setOnClickListener(this);
        binding.picutreContainer.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_container:
                Verification();
                break;
        }
    }


    private void Verification() {
        if (binding.firstNameEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please fill your firstname! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.lastnameEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please fill your lastname! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.countryEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please fill your country! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.telEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please fill your phone number! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.emailEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please enter your mail!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.passwordEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.confirmnPasswordEdt.getText().toString().contentEquals("")) {
            Toast.makeText(getContext(), "Please enter your verification password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CheckPassword()){
            Toast.makeText(getContext(), "Please verifier the format of password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(binding.emailEdt.getText().toString())) {
            Toast.makeText(getContext(), "Please enter username with valid mail!", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (!isCINValid(mIdentity.getText().toString())) {
//            Toast.makeText(getContext(), "Pleaser enter identity with correct format", Toast.LENGTH_SHORT).show();
//            return;
//        }
        SignUp();
    }

    private boolean CheckPassword (){
       if (binding.passwordEdt.getText().toString().matches("^[a-zA-Z]*$")){
           if (binding.passwordEdt.getText().toString().length()>= 8){
               return true;
           } else {
               return false;
           }
       } else {
           return false;
       }
    }

    private void SignUp() {
        binding.registerTxt.setVisibility(View.INVISIBLE);
        binding.aviSignUp.setVisibility(View.VISIBLE);
        // creation  du compte temporaire avec l email et password
        mAuth.createUserWithEmailAndPassword(binding.emailEdt.getText().toString(), binding.passwordEdt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "createUserWithEmail:success");
                    // prendre le utilisateur courant
                    FirebaseUser user = mAuth.getCurrentUser();
                    SaveUser(user, Calendar.getInstance().getTime());
                } else {
                    binding.registerTxt.setVisibility(View.VISIBLE);
                    binding.aviSignUp.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void SaveUser(FirebaseUser user, Date created_at) {
        // prendre le nom d'utilisateur tape avec la modification de premier lettre en majuscule
        String firstname = binding.firstNameEdt.getText().toString().substring(0,1).toUpperCase() +binding.firstNameEdt.getText().toString().substring(1);

        // prendre le prenom d'utilisateur tape avec la modification de premier lettre en majuscule
        String lastname = binding.lastnameEdt.getText().toString().substring(0,1).toUpperCase()  + binding.lastnameEdt.getText().toString().substring(1);
        // creation d'entite user avec les donnees saisie dans la formulaire d'inscription
        final User FullUser = new User(user.getUid().toString(), firstname, lastname, binding.emailEdt.getText().toString(), binding.telEdt.getText().toString(), binding.countryEdt.getText().toString(),binding.passwordEdt.getText().toString(),  "", created_at, created_at);
        // creation du child , avec le nom de child identifier d'utilisateur & avec les donness de l'entites (setValue(FullUser)
        myRef.child(user.getUid()).setValue(FullUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // en cas du compli mettre le loading du buttton invisible
                binding.aviSignUp.setVisibility(View.INVISIBLE);
                // passser a l'activite home (principale , main)
                CreateCompte(FullUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // text du buttom visible
                binding.registerTxt.setVisibility(View.VISIBLE);
                // loading invisible
                binding.aviSignUp.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void CreateCompte(final User maincurrentUser) {
        Random rnd = new Random();
        final Compte compte = new Compte(UUID.randomUUID().toString(),maincurrentUser.getNom()+" "+maincurrentUser.getPrenom(),"TN59"+String.valueOf(rnd.nextInt(1000000000)), TypeCompteEnum.Courant,String.valueOf(rnd.nextInt(1000)),"0",maincurrentUser);
        CompteRef.child(maincurrentUser.getId()).child(compte.getNum_compte()).setValue(compte).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               CreateCarte(maincurrentUser,compte);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Verifier votre conexion!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void CreateCarte(final User maincurrentUser, final Compte compte) {
        Random rnd = new Random();
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 3); // to get previous year add -1
        Date nextYear = cal.getTime();
        String numCarte = "4200"+String.valueOf(rnd.nextInt(1000000000));
        final Cartes carte = new Cartes(numCarte,"credit","debit",maincurrentUser.getNom(),maincurrentUser.getPrenom(),nextYear,compte.getNum_compte(),maincurrentUser);
        CarteRef.child(maincurrentUser.getId().toString()).child(numCarte).setValue(carte).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UpdateUI(maincurrentUser, compte, carte);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Verifier votre conexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateUI(User maincurrentUser, Compte compte, Cartes carte) {
        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("currentfulluser",maincurrentUser);
        i.putExtra("compte",compte);
        i.putExtra("carte",carte);
        binding.aviSignUp.setVisibility(View.INVISIBLE);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.alpha, R.anim.slide_down);
        getActivity().finish();
    }

}
