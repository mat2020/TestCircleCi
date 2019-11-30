package com.uottawa.project;

import java.util.ArrayList;

public class Clinic {

    /*
     * The name of the Clinic.
     */
    private String name;

    /*
     * The arrayList of services the Clinic provides.
     */
    private ArrayList<Service> services= new ArrayList<Service>();

    /*
     * The arraylist of employees who work at the clinic.
     */
    private ArrayList<Employee> employees= new ArrayList<>();

    /*
     * The array of hours the Clinic opens at.
     */
    protected int[] openHours;

    /*
     * The array of hours the Clinic closes at.
     */
    protected int[] closeHours;

    /*
     * The arraylist of comments of the clinic.
     */
    private ArrayList<String> commentStorage= new ArrayList<>();

    /*
     * The arraylist of rating of the clinic.
     */
    private ArrayList<Float> ratingStorage= new ArrayList<>();

    private String id;


    public Clinic() {
    }

    /**
     * Creates a new Clinic.
     * @param name a string with the name of the Clinic
     */
    public Clinic(String name) {

        this.name = name;

    }


    //These methods will be properly implemented when Clinics are added


    /**
     * Adds a service to the Clinic.
     * @param service the Service to be added to the clinic
     */
    public void addService(Service service) {
        this.services.add(service);
    }

    /**
     * Removes a service the Clinic no longer offers.
     * @param service the Service to be removed from the Clinic
     */
    public void removeService(Service service) {
        if (this.services.contains(service)) {
            this.services.remove(service);
        } else {
            throw new IllegalArgumentException("Service not in Clinic.");
        }

    }

    /**
     * Sets the hours of operation of the Clinic.
     * @param hours
     */
    public void setHours(int[] hours) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Sets the Openhours of operation of the Clinic.
     */
    public void setOpenHours(int[] hours) {
        this.openHours = hours;
    }

    /**
     * Sets the hours of operation of the Clinic.
     */
    public void setClosingHours(int[] hours) {
        this.closeHours = hours;
    }
    /**
     * Adds the employee to the clinic.
     * @param employee the employee to be added
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public String getID(){return id;}

    public void setID(String id){this.id=id;}

    public ArrayList<Service>  getServices() {
        return services;
    }
}
