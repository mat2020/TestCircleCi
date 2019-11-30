package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    DatabaseReference database;
    List<Account> users;
    private DbHandler mydb = new DbHandler();
    DatabaseReference Clinics;
    static ArrayList<Clinic> ClinicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance().getReference("users");
        ClinicList = new ArrayList<Clinic>();

        //for test default clinic
        ClinicList.add(new Clinic("Clinic1"));
        ClinicList.add(new Clinic("Clinic2"));

        Clinics = FirebaseDatabase.getInstance().getReference("Clinics");
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                users.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Account user = postSnapshot.getValue(Account.class);
                    users.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });


        Clinics.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
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

    public void onRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivityForResult(intent,0);

    }

    public void onLogin(View view) {
        mydb.addAdmin(database,users); // check to makes sure admin exists

        //Check that the password and username are valid here
        String accountType ="";
        String stringUsername = ((TextView)findViewById(R.id.username)).getText().toString();
        String stringPassword = ((TextView)findViewById(R.id.password)).getText().toString();
        boolean validData = true;

        if (!mydb.exists(stringUsername, "Username",users)){
            validData = false;
            Toast.makeText(getApplicationContext(), "Username is wrong", Toast.LENGTH_LONG).show();
        }



        String hex = "";
        try{
            //hashing the password to SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(stringPassword.getBytes(StandardCharsets.UTF_8));

            //convertting to hexadecimal

            for (int i =0; i < passwordHash.length; i++) {
                hex = hex + String.format("%02x", passwordHash[i]);
            }

        }
        catch(Exception e){
            hex = "";
        }


        if (validData) {
            Account dbUser = mydb.getData(stringUsername, users);
            String dbpassword = dbUser.getPassword();


            if (!hex.equals( dbpassword)){
                Toast.makeText(getApplicationContext(), "Username or password is wrong", Toast.LENGTH_LONG).show();
                validData = false;

            } else {
                Intent intent;
                //Admin user

                if (dbUser.getAccountType().equals("Admin")) {
                    Admin userAdmin = new Admin(dbUser);
                    intent = new Intent(getApplicationContext(), AdminScreen.class);

                //Employee user
                } else if (dbUser.getAccountType().equals("Employee")) {
                    //check if employee is part of a clinic
                    boolean hasClinic = false;
                    Clinic employeesClinic= null;

                    for (int i = 0; i< ClinicList.size();i++){
                        ArrayList<Employee> next = ClinicList.get(i).getEmployees();
                        for(int j = 0; j< next.size(); j++){
                            if (next.get(j).getUsername().equals(stringUsername)){
                                hasClinic = true;
                                employeesClinic = ClinicList.get(i);
                            }
                        }

                    }


                    if (!hasClinic) {
                        intent = new Intent(getApplicationContext(), EmployeeScreenWithoutClinic.class);

                    } else {
                        intent = new Intent(getApplicationContext(), EmployeeScreen.class);
                        intent.putExtra("clinic",employeesClinic.getName());
                    }
                    intent.putExtra("accountType","Employee");
                //Patient (or is an error, the least amount of damage can be done with a Patient Account)
                } else {
                    intent = new Intent(getApplicationContext(), PatientScreen.class);
                    intent.putExtra("accountType","Patient");
                }

                intent.putExtra("username",stringUsername);
                startActivityForResult(intent, 0);
            }
        }

        /*if (validData){
            Intent intent;
            if (dbUser.getClass().equals(Admin.class) )
            Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
            intent.putExtra("username",stringUsername);
            intent.putExtra("accoutType",accountType);
            startActivityForResult(intent, 0);
        }*/
    }
}
