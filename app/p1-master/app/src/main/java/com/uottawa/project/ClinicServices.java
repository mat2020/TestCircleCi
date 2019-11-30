package com.uottawa.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinicServices extends AppCompatActivity {
    Clinic clinic;
    DatabaseReference databaseService;
    DatabaseReference databaseClinics;
    List<Service> services;
    String clinicName;

    ListView listViewAllServices;
    ListView listViewCurrentServices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_services);

        databaseService = FirebaseDatabase.getInstance().getReference("Services");
        databaseClinics = FirebaseDatabase.getInstance().getReference("Clinics");
        listViewAllServices = (ListView) findViewById(R.id.allServiceList);
        listViewCurrentServices = (ListView) findViewById(R.id.currentServiceList);
        services = new ArrayList<>();

        Intent welcome = this.getIntent();

        clinicName = welcome.getStringExtra("clinicName");
        listViewAllServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showServiceAdd(service);
                return true;
            }
        });

        listViewCurrentServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showServiceDelete(service);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                services.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }
                ServiceList ServicesAdapterAll = new ServiceList(ClinicServices.this, services);
                listViewAllServices.setAdapter(ServicesAdapterAll);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

        databaseClinics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic databaseClinic = postSnapshot.getValue(Clinic.class);
                    if (databaseClinic.getName().equals(clinicName)){
                        clinic = databaseClinic;
                        break;
                    }
                }

                ServiceList ServicesAdapterCurrent = new ServiceList(ClinicServices.this, clinic.getServices());
                listViewCurrentServices.setAdapter(ServicesAdapterCurrent);


            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }

    private void showServiceAdd(final Service newService) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service, null);
        dialogBuilder.setView(dialogView);


        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btnAddConfirm);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService(newService);
                b.dismiss();
            }
        });
    }

    private void showServiceDelete(final Service newService) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btnDeleteConfirm);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeService(newService);
                b.dismiss();
            }
        });
    }


    public void onDone(View view) {
        Intent returnIntent = new Intent();

        setResult(RESULT_OK, returnIntent);
        finish();
    }



    public void addService (Service service){
        boolean check = false;
        for (int count = 0 ; count < clinic.getServices().size() ; count++){
            if ((clinic.getServices().get(count).getName()).equals (service.getName())){
               check = true;
               break;
            }
        }

        if (check == false){
            clinic.addService(service);
        }

        DatabaseReference dR = databaseClinics.child(clinic.getID());
        dR.setValue(clinic);


    }

    public void removeService (Service service){
        for (int count = 0 ; count < clinic.getServices().size() ; count++){
            if ((clinic.getServices().get(count).getName()).equals (service.getName())){
                clinic.removeService(service);
                break;
            }
        }

        DatabaseReference dR = databaseClinics.child(clinic.getID());
        dR.setValue(clinic);

    }
}
