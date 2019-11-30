package com.uottawa.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageServices extends AppCompatActivity {

    EditText editTextName;
    EditText editTextRate;
    EditText editTextRole;
    Button buttonAddService;
    ListView listViewServices;
    Button buttonExit;

    DatabaseReference databaseProducts;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        editTextName = (EditText) findViewById(R.id.ServiceName);
        editTextRate = (EditText) findViewById(R.id.ServiceRate);
        editTextRole =(EditText) findViewById(R.id.ServiceJob);
        listViewServices = (ListView) findViewById(R.id.ServiceList);
        buttonAddService = (Button) findViewById(R.id.addService);
        buttonExit = (Button) findViewById(R.id.ExitService);

        services = new ArrayList<>();

        databaseProducts = FirebaseDatabase.getInstance().getReference("Services");

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getName(), service.getRate(), service.getRole());
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
                services.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }
                ServiceList ServicesAdapter = new ServiceList(ManageServices.this, services);
                listViewServices.setAdapter(ServicesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private void showUpdateDeleteDialog(final String serviceId, String serviceName, float serviceRate, String serviceRole) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_servicedialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.updateName);
        final EditText editTextRate  = (EditText) dialogView.findViewById(R.id.updateRate);
        final EditText editTextRole = (EditText) dialogView.findViewById(R.id.updateRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(serviceName);

        editTextName.setText(serviceName);
        editTextRate.setText(Float.toString(serviceRate));
        editTextRole.setText(serviceRole);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try
                {
                String serviceName = editTextName.getText().toString().trim();
                float serviceRate = Float.parseFloat(editTextRate.getText().toString());
                String serviceRole = editTextRole.getText().toString().trim();
                if (!TextUtils.isEmpty(serviceName)&& !TextUtils.isEmpty(serviceRole)) {
                    updateService(serviceId, serviceName, serviceRate, serviceRole);
                    b.dismiss();
                }
                }
                catch (NumberFormatException e)
                {
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                b.dismiss();
            }
        });
    }

    private void updateService(String id, String name, float rate, String role) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Services").child(id);
        Service service = new Service(rate,name,role,id);
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "service updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteService(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Services").child(id);
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "service deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    public void onExit(View view){
        finish();
    }
    public void onAdd(View view){

        //validating all fields
        boolean valid = true;
        if (TextUtils.isEmpty(editTextName.getText().toString()) ){
            valid = false;
            Toast.makeText(this, "Please enter a service Name", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(editTextRole.getText().toString())){
            valid = false;
            Toast.makeText(this, "Please enter a service Role", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(editTextRate.getText().toString())){
            valid = false;
            Toast.makeText(this, "Please enter a service Rate", Toast.LENGTH_LONG).show();
        }

        try
        {
            Float.parseFloat(editTextRate.getText().toString());

        }
        catch (NumberFormatException e)
        {
            valid = false;
            Toast.makeText(this, "Rate entered in incorrect format", Toast.LENGTH_LONG).show();
        }


        for (int i = 0; i< services.size(); i++){
            if (services.get(i).getName().equals(editTextName.getText().toString().trim())){
                valid = false;
                Toast.makeText(this, "Service already exists", Toast.LENGTH_LONG).show();
                break;
            }
        }


        if (valid){
            String id = databaseProducts.push().getKey();
            Service newService = new Service();
            newService.setName(editTextName.getText().toString().trim());
            newService.setRate(Float.parseFloat(editTextRate.getText().toString()));
            newService.setRole(editTextRole.getText().toString().trim());
            newService.setId(id);


            databaseProducts.child(id).setValue(newService);


            editTextName.setText("");
            editTextRate.setText("");
            editTextRole.setText("");
            Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
        }


    }



}
