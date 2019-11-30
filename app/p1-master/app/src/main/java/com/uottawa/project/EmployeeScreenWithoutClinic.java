package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeScreenWithoutClinic extends AppCompatActivity {

    private DbHandler mydb;
    DatabaseReference database;
    List<Clinic> ClinicList;
    String stringUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_screen_without_clinic);

        Intent welcome = this.getIntent();

         stringUsername = welcome.getStringExtra("username");
        mydb = new DbHandler();
        String dbName = stringUsername;
        ((TextView)findViewById(R.id.welcomeMsg)).setText("Welcome "+dbName+". You are logged in as an Employee.");
        ClinicList = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("Clinics");
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                ClinicList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic clinic = postSnapshot.getValue(Clinic.class);
                    ClinicList.add(clinic);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    public void onCreateClinic(View view) {
        String stringClinic = ((TextView)findViewById(R.id.ClinicName)).getText().toString();

        boolean exists = false;
        for (int i = 0; i< ClinicList.size();i++){
           if (stringClinic.equals(ClinicList.get(i).getName())){
               exists = true;
           }

        }
        if (!exists){
            Clinic newClinic = new Clinic(stringClinic);
            newClinic.addEmployee(new Employee(stringUsername));
            String id = database.push().getKey();
            newClinic.setID(id);
            database.child(id).setValue(newClinic);

            Intent intent = new Intent(getApplicationContext(), EmployeeScreen.class);
            intent.putExtra("clinic",stringClinic);
            intent.putExtra("username",stringUsername);
            startActivityForResult(intent, 0);

            Intent returnIntent = new Intent();

            setResult(RESULT_OK, returnIntent);
            finish();


        }
        else{
            Toast.makeText(getApplicationContext(), "Clinic already exists", Toast.LENGTH_LONG).show();
        }

    }

    public void onJoinClinic(View view) {
        String stringClinic = ((TextView)findViewById(R.id.ClinicName)).getText().toString();



        for (int i = 0; i< ClinicList.size();i++){
            if (stringClinic.equals(ClinicList.get(i).getName())){
                Clinic newClinic = ClinicList.get(i);
                newClinic.addEmployee(new Employee(stringUsername));
                String id = newClinic.getID();
                DatabaseReference dR = database.child(id);
                dR.setValue(newClinic);

                Intent intent = new Intent(getApplicationContext(), EmployeeScreen.class);
                intent.putExtra("clinic",stringClinic);
                intent.putExtra("username",stringUsername);
                startActivityForResult(intent, 0);

                Intent returnIntent = new Intent();

                setResult(RESULT_OK, returnIntent);
                finish();

            }

        }
        Toast.makeText(getApplicationContext(), "Clinic does not exist", Toast.LENGTH_LONG).show();

    }

    public void onLogout(View view) {
        Intent returnIntent = new Intent();

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
