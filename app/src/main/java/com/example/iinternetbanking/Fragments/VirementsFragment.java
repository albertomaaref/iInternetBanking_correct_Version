package com.example.iinternetbanking.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.iinternetbanking.Models.Compte;
import com.example.iinternetbanking.Models.demandeVirement;
import com.example.iinternetbanking.R;
import com.example.iinternetbanking.databinding.FragmentVirementsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;
import java.util.UUID;

import static com.example.iinternetbanking.Activities.MainActivity.CurrentCompte;
import static com.example.iinternetbanking.Activities.MainActivity.CurrentFullUser;

public class VirementsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentVirementsBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference myRefVirement;
    private Calendar myCalendarB;
    private boolean dateVirement = false;
    private ArrayList<String> numComptes = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String numCompteD = "";

    public VirementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRefVirement = database.getReference("Virements");
        database.getReference("Comptes").child(CurrentFullUser.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                numComptes.add(dataSnapshot.getValue(Compte.class).getNum_compte());
                adapter.notifyDataSetChanged();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_virements, container, false);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, numComptes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinVirements.setAdapter(adapter);
        binding.spinVirements.setOnItemSelectedListener(this);
        binding.demandeVirementContainer.setOnClickListener(this);
        myCalendarB = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener timeB = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarB.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendarB.set(Calendar.MINUTE, minute);
                dateVirement = true;
                updateLabelB();
            }
        };

        final DatePickerDialog.OnDateSetListener dateB = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarB.set(Calendar.YEAR, year);
                myCalendarB.set(Calendar.MONTH, month);
                myCalendarB.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(getContext(), timeB, myCalendarB.
                        get(Calendar.HOUR_OF_DAY), myCalendarB.get(Calendar.MINUTE), true).show();
            }
        };

        binding.dateVirementEdittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), dateB, myCalendarB
                        .get(Calendar.YEAR), myCalendarB.get(Calendar.MONTH),
                        myCalendarB.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demande_virement_container:
                SaveVirement();
                break;
        }
    }

    private void updateLabelB() {
        String myFormat = "dd/MM/yyyy HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        binding.dateVirementEdittext.setText(sdf.format(myCalendarB.getTime()));

    }

    private void SaveVirement() {
        if (dateVirement = false) {
            return;
        }
        if (binding.numBEdittext.getText().toString().contentEquals("")) {
            return;
        }
        if (binding.dateVirementEdittext.getText().toString().contentEquals("")) {
            return;
        }
        if (binding.montantVirementEdittext.getText().toString().contentEquals("")) {
            return;
        }
        if (numCompteD.contentEquals("")) {
            return;
        }
        binding.demandeVirementContainer.setEnabled(false);
        binding.aviVirements.setVisibility(View.VISIBLE);
        binding.demandeVirementTxt.setVisibility(View.INVISIBLE);
        demandeVirement demandeVirement = new demandeVirement(numCompteD, binding.numBEdittext.getText().toString(), binding.montantVirementEdittext.getText().toString(), myCalendarB.getTime(), Calendar.getInstance().getTime(), null);
        myRefVirement.child(CurrentFullUser.getId()).child(UUID.randomUUID().toString()).setValue(demandeVirement).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                binding.demandeVirementContainer.setEnabled(true);
                binding.aviVirements.setVisibility(View.INVISIBLE);
                binding.demandeVirementTxt.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "demande de virement envoyée avec succés", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.demandeVirementContainer.setEnabled(true);
                binding.aviVirements.setVisibility(View.INVISIBLE);
                binding.demandeVirementTxt.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "demande de virement n'est pass envoyée, Vérifiez votre connexion!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numCompteD = numComptes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
