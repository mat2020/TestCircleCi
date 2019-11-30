package com.uottawa.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResult extends AppCompatActivity {
    private RecyclerView listClinic;
    private RecyclerView.LayoutManager layout;
    private RecyclerView.Adapter adapter;
    private List<Clinic> ClinicList;
    private List<Float> ratingsList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        ClinicList = Search.ResultClinic;
        ratingsList = Search.ratingsList;
        intent = getIntent();

        //dropdown list
        listClinic = (RecyclerView) findViewById(R.id.listSearchResult);
        layout = new LinearLayoutManager(this);
        listClinic.setLayoutManager(layout);
        final Intent newIntent = new Intent(this,BookAppointment.class);
        adapter = new searchAdapter(ClinicList, new searchAdapter.searchViewListener() {

            @Override
            public void onSelect(Clinic a) {

                Toast.makeText(getApplicationContext(), "Clinic selected: "+ a.getName(), Toast.LENGTH_LONG).show();

                newIntent.putExtra("username",intent.getStringExtra("username"));
                newIntent.putExtra("clinic",a.getName());
                startActivityForResult(newIntent, 0);

            }

    }, ratingsList);
       listClinic.setAdapter(adapter);

    }
}

