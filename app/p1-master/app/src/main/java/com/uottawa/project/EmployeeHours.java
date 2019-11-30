package com.uottawa.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeHours extends AppCompatActivity {

    private int currentStart;
    private int currentEnd;
    private long selectedDate;

    DatabaseReference database;
    Hours currentEmployeeHours;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_hours);

        Intent welcome = this.getIntent();

        username = welcome.getStringExtra("username");


        database = FirebaseDatabase.getInstance().getReference("Hours");



        //get the currentEmployeeHours here
        //currentEmployeeHours = new Hours("Jeff"); //for testing

        //updateScreen changes the display to mirror the working hours in the database
        //you will need to change it please
        CalendarView cal = (CalendarView) findViewById(R.id.calendar);
        selectedDate = cal.getDate();


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                selectedDate = c.getTimeInMillis();
                updateScreen();
            }
        });

        SeekBar start = (SeekBar) findViewById(R.id.startTimeSelection);
        start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentStart = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (currentEnd < currentStart) {
                    Toast.makeText(getApplicationContext(), "Endtime of shift must be after start.", Toast.LENGTH_SHORT).show();
                } else if (currentEnd == currentStart) {
                    Toast.makeText(getApplicationContext(), "Endtime cannot be the same as the start time.", Toast.LENGTH_SHORT).show();
                }

                TextView selected = (TextView) findViewById(R.id.selectedTime);
                selected.setText("Time Selected: "+currentStart+":00 - "+currentEnd+":00");
            }
        });

        SeekBar end = (SeekBar) findViewById(R.id.endTimeSelection);
        end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentEnd = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (currentEnd < currentStart) {
                    Toast.makeText(getApplicationContext(), "Endtime of shift must be after start.", Toast.LENGTH_SHORT).show();
                } else if (currentEnd == currentStart) {
                    Toast.makeText(getApplicationContext(), "Endtime cannot be the same as the start time.", Toast.LENGTH_SHORT).show();
                }
                System.out.println("end");

                TextView selected = (TextView) findViewById(R.id.selectedTime);
                selected.setText("Time Selected: "+currentStart+":00 - "+currentEnd+":00");
            }
        });

        Switch workingSwitch = (Switch) findViewById(R.id.isWorking);
        workingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.endTimeSelection).setEnabled(true);
                    findViewById(R.id.startTimeSelection).setEnabled(true);

                    TextView selected = (TextView) findViewById(R.id.selectedTime);
                    selected.setText("Time Selected: "+currentStart+":00 - "+currentEnd+":00");
                } else {
                    findViewById(R.id.endTimeSelection).setEnabled(false);
                    findViewById(R.id.startTimeSelection).setEnabled(false);

                    TextView selected = (TextView) findViewById(R.id.selectedTime);
                    selected.setText("Time Selected: None");
                }
            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Hours hours = postSnapshot.getValue(Hours.class);
                    if (hours.getName().equals(username)){
                        currentEmployeeHours = hours;
                        break;
                    }
                }


                if (currentEmployeeHours == null){
                    currentEmployeeHours = new Hours(username);
                    currentEmployeeHours.setId(database.push().getKey());
                    database.child(currentEmployeeHours.getId()).setValue(currentEmployeeHours);
                }
                updateScreen();
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    //reads from the database
    private void updateScreen() {
        //get the updated hours from the database

        /*
            CalendarView cal = (CalendarView) findViewById(R.id.calendar);

         	This may be useful, IDK?:

         	cal.getDate();

            Gets the selected date in milliseconds since January 1, 1970 00:00:00 in TimeZone#getDefault() time zone.
            Returns a long.
         */

        int[] hours = currentEmployeeHours.getHours(selectedDate);

        Boolean isWorking; //if the person is working that day
        if (hours == null) {
            isWorking = false;
        } else {
            isWorking = true;
            currentStart = hours[0]; //the time the shift starts at
            currentEnd = hours[1]; //the time the shift ends at
        }

        Switch workingSwitch = (Switch) findViewById(R.id.isWorking);
        SeekBar start = (SeekBar) findViewById(R.id.startTimeSelection);
        SeekBar end = (SeekBar) findViewById(R.id.endTimeSelection);

        if (!isWorking){
            workingSwitch.setChecked(false);
            start.setProgress(0);
            end.setProgress(0);
            end.setEnabled(false);
            start.setEnabled(false);

            TextView selected = (TextView) findViewById(R.id.selectedTime);
            selected.setText("Time Selected: None");
        } else {
            workingSwitch.setChecked(true);
            start.setProgress(currentStart);
            end.setProgress(currentEnd);

            TextView selected = (TextView) findViewById(R.id.selectedTime);
            selected.setText("Time Selected: "+currentStart+":00 - "+currentEnd+":00");
        }
    }

    public void onDone(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    //writes to the database
    public void onUpdate(View view) {
        if (currentEnd < currentStart) {
            Toast.makeText(getApplicationContext(), "Unable to update hours.\nEndtime of shift must be after start.", Toast.LENGTH_SHORT).show();
            return;
        } else if (currentEnd == currentStart) {
            Toast.makeText(getApplicationContext(), "Unable to update hours.\nEndtime cannot be the same as the start time.", Toast.LENGTH_SHORT).show();
            return;
        }

        //update the hours class here to prepare to send to database
        Switch workingSwitch = (Switch) findViewById(R.id.isWorking);
        CheckBox repeat = (CheckBox) findViewById(R.id.weeklyRepeat);
        int start;
        int end;
        if (workingSwitch.isChecked()) {
            //send currentEnd as the end of the shift and currentStart as the start of the shift
            start = currentStart;
            end = currentEnd;
        } else {
            //-1 as start and end means not working
            start = -1;
            end = -1;
        }

        currentEmployeeHours.setHours(selectedDate, start, end, repeat.isChecked());
        repeat.setChecked(false);



        DatabaseReference dR = database.child(currentEmployeeHours.getId());
        dR.setValue(currentEmployeeHours);
        Toast.makeText(getApplicationContext(), "Hours Updated", Toast.LENGTH_SHORT).show();
    }
}
