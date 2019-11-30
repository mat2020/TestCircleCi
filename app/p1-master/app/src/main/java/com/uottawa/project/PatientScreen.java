package com.uottawa.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientScreen extends AppCompatActivity {

    private RecyclerView currentAppointments;
    private RecyclerView.LayoutManager layout;
    private RecyclerView.Adapter adapter;
    private ArrayList appointments;
    private String username;

    DatabaseReference database;
    Intent intent;

    private AppointmentAdapter.AppointmentViewHolder forRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_screen);

        intent = getIntent();

        TextView welcome = (TextView) findViewById(R.id.welcomeMsg);
        welcome.setText("Welcome "+intent.getStringExtra("username")+". You are logged in as a Patient.");
        username = intent.getStringExtra("username");
        currentAppointments = (RecyclerView) findViewById(R.id.currentAppointments);

        layout = new LinearLayoutManager(this);
        currentAppointments.setLayoutManager(layout);

        appointments = new ArrayList<Appointment>();


        database = FirebaseDatabase.getInstance().getReference("Appointments");


        //get the appointments from database

        //for testing
        //appointments.add(new Appointment("Nov. 11, 2019","9:30", "Moe's", intent.getStringExtra("username")));
        //appointments.add(new Appointment("Nov. 12, 2019","10:30", "Molly's", intent.getStringExtra("username")));


        adapter = new AppointmentAdapter(appointments, new AppointmentAdapter.AppointmentViewListener() {
            @Override
            public void onCancel(Appointment a, AppointmentAdapter.AppointmentViewHolder holder) {
                //System.out.println(a.getDate());
                int index = appointments.indexOf(a);
                appointments.remove(a);
                DatabaseReference dR = database.child(a.getID());
                dR.removeValue();
                //remove from database?
                //should we notify the staff?

                if (holder.getCancel().getText().equals("Cancel")) {
                    Toast.makeText(getApplicationContext(), "Appointment Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Appointment Removed", Toast.LENGTH_LONG).show();
                }

                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onCheckIn(Appointment a, AppointmentAdapter.AppointmentViewHolder holder) {
                //System.out.println(a.getTime());
                if (! a.isCheckedIn()) {
                    a.checkIn();
                    holder.getCheckIn().setText("Review Visit");
                    holder.getCancel().setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Checked In", Toast.LENGTH_LONG).show();
                } else {
                    forRating = holder;
                    Intent intent = new Intent(getApplicationContext(), RateClinic.class);
                    intent.putExtra("clinic", a.getClinic());
                    intent.putExtra("patient", a.getPatient());
                    startActivityForResult(intent, 0);
                    DatabaseReference dR = database.child(a.getID());
                    dR.removeValue();
                }
            }
        });
        currentAppointments.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                appointments.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Appointment appointment = postSnapshot.getValue(Appointment.class);
                    if (appointment.getPatient().equals(intent.getStringExtra("username")))
                        appointments.add(appointment);
                }



                currentAppointments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });


    }

    private void runDatabase () {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appointments.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Appointment appointment = postSnapshot.getValue(Appointment.class);
                    if (appointment.getPatient().equals(intent.getStringExtra("username")))
                        appointments.add(appointment);
                }
                System.out.println("database");


                currentAppointments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

    }

    public void onSearchClinics(View view) {

        Intent myIntent = new Intent(this,Search.class);
        myIntent.putExtra("username",intent.getStringExtra("username"));
        startActivity(myIntent);

        runDatabase();

        //how to call the booking appointment activity:
        //have a button the Clinic's page in the search that when clicked allows the patient to book
        //an appointment, then use this code below
        /*Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
        intent.putExtra("username", "Joe");
        intent.putExtra("clinic", "Bill");
        startActivityForResult(intent, 0);*/


    }
    public void onLogout(View view) {
        //get activity about rating
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }



    /*public void onCancel(View view) {
        System.out.println("cancel");

    public void onRating(View view) {
        //get activity about rating
        Intent ratingPage = new Intent(this,Rating.class);
        startActivity(ratingPage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);;
        String returnType = data.getStringExtra("result");

        if (returnType.equals("post")) {
            forRating.getCheckIn().setEnabled(false);
            forRating.getCancel().setEnabled(true);
            forRating.getCancel().setText("Remove");
            Toast.makeText(getApplicationContext(), "Review Posted", Toast.LENGTH_LONG).show();
        } else {
            System.out.println(returnType);
        }

    }*/
}
