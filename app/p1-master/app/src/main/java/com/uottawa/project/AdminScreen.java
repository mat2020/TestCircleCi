package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminScreen extends AppCompatActivity {

    private DbHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        Intent welcome = this.getIntent();

        String stringUsername = welcome.getStringExtra("username");
        mydb = new DbHandler();
        String dbName = stringUsername;
        ((TextView)findViewById(R.id.welcomeMsg)).setText("Welcome "+dbName+". You are logged in as Admin.");
    }

    public void onManageServices (View view) {
        Intent intent = new Intent(getApplicationContext(), ManageServices.class);
        startActivityForResult(intent,0);
    }

    public void onManageAccounts(View view) {
        Intent intent = new Intent(getApplicationContext(), ManageAccounts.class);
        startActivityForResult(intent, 0);
    }
    public void onManageClinics (View view) {
        Intent intent = new Intent(getApplicationContext(), ManageClinics.class);
        startActivityForResult(intent,0);
    }

    public void onLogout(View view) {
        Intent returnIntent = new Intent();
        //We might use this to send stuff to the next page??
        //returnIntent.putExtra("imageID", selectedImage.getId());

        setResult(RESULT_OK, returnIntent);
        finish();
    }



}
