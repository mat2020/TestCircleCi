package com.uottawa.project;

public class Review {

    private String clinic;
    private String patient;
    private String comment;
    private float rating;
    private String id;

    public Review(String clinic, String patient, String comment, float rating) {
        this.clinic = clinic;
        this.patient = patient;
        this.comment = comment;
        this.rating = rating;
    }

    public Review() {}

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPatient() {
        return patient;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getClinic() {
        return clinic;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }
}
