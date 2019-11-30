package com.uottawa.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageClinics extends AppCompatActivity {


    ListView listViewClinics;
    Button buttonExit;

    DatabaseReference databaseProducts;

    List<Clinic> clinics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_clinics);
        listViewClinics = (ListView) findViewById(R.id.ClinicList);
        buttonExit = (Button) findViewById(R.id.ExitClinic);

        clinics = new ArrayList<>();

        databaseProducts = FirebaseDatabase.getInstance().getReference("Clinics");

        listViewClinics.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Clinic clinic = clinics.get(i);
                showUpdateDeleteDialog(clinic.getName(), clinic.getID());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                clinics.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Clinic clinic = postSnapshot.getValue(Clinic.class);
                    clinics.add(clinic);
                }
                ClinicList ClinicAdapter = new ClinicList(ManageClinics.this, clinics);
                listViewClinics.setAdapter(ClinicAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private void showUpdateDeleteDialog(String clinicName,final String clinicId ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_clinicdialog, null);
        dialogBuilder.setView(dialogView);
        final TextView delClinicName = (TextView) dialogView.findViewById(R.id.DelClinicName);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.DelClinicBtn);

        dialogBuilder.setTitle(clinicName);

        delClinicName.setText(clinicName);
        final AlertDialog b = dialogBuilder.create();
        b.show();



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClinic(clinicId);
                b.dismiss();
            }
        });
    }


    private boolean deleteClinic(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Clinics").child(id);
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "clinic deleted", Toast.LENGTH_LONG).show();
        return true;
    }



    public void onExit(View view){
        finish();
    }


}
