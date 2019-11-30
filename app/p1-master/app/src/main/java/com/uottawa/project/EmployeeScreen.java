package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeScreen extends AppCompatActivity {

    private DbHandler mydb;
    private String username;
    DatabaseReference database;
    private String ClinicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_screen);

        Intent welcome = this.getIntent();
        database = FirebaseDatabase.getInstance().getReference("Clinics");
        username = welcome.getStringExtra("username");
        ClinicName = welcome.getStringExtra("clinic");
        mydb = new DbHandler();
        String dbName = username;
        ((TextView)findViewById(R.id.welcomeMsg)).setText("Welcome "+dbName+". You are logged in as an Employee.");
    }






    public void onManageServices(View view) {
        //add changing services here
        Intent intent = new Intent(getApplicationContext(), ClinicServices.class);
        intent.putExtra("clinicName", ClinicName);
        startActivityForResult(intent, 0);
    }

    public void onManageHours(View view) {
        //add changing hours here
        Intent intent = new Intent(getApplicationContext(), EmployeeHours.class);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

    public void onManageClinicHours(View view) {
        //add changing hours here
        Intent intent = new Intent(getApplicationContext(), ClinicHours.class);
        intent.putExtra("clinicName", ClinicName);
        startActivityForResult(intent, 0);
    }

    public void onLogout(View view) {
        Intent returnIntent = new Intent();

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
