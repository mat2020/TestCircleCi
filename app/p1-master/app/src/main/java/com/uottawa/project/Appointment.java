package com.uottawa.project;

public class Appointment {

    /*
     * Stores the date of the appointment.
     */
    private String date;

    /*
     * Stores the time for the appointment.
     */
    private String time;

    /*
     * Stores the name of clinic the appointment is booked at.
     */
    private String clinic;

    /*
     * Stores the name of Patient the appointment is booked for.
     */
    private String patient;

    /*
     * The id of the appointment in the database.
     */
    private String id;

    /*
     * True if the patient has checked in for the appointment.
     */
    private boolean checkedIn;

    public Appointment(String date, String time, String clinic, String patient) {
        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.patient = patient;
    }

    public Appointment() {}

    public void checkIn() {
        this.checkedIn = true;
    }

    public boolean isCheckedIn() {
        return this.checkedIn;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public  boolean equals (Appointment b){
        return (this.date == b.date && this.time == b.time && this.clinic == b.clinic );


    }
}
