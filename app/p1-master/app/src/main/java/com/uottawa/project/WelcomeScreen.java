package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class WelcomeScreen extends AppCompatActivity {

    private DbHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent welcome = this.getIntent();

        String stringUsername = welcome.getStringExtra("username");
        String accountType = welcome.getStringExtra("accountType");
        mydb = new DbHandler();
        String dbName = stringUsername;
        ((TextView)findViewById(R.id.welcomeMsg)).setText("Welcome "+dbName+". You are logged in as "+accountType+".");

    }

    public void onLogout(View view) {
        Intent returnIntent = new Intent();
        //We might use this to send stuff to the next page??
        //returnIntent.putExtra("imageID", selectedImage.getId());

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
