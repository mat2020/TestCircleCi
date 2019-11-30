package com.uottawa.project;

public class Employee extends Account {

    /*
     * The clinic the employee belongs to.
     */
    private Clinic clinic;

    /**
     * Creates an Employee Account.
     * @param password a string with the password
     * @param username a string with the username
     * @param firstName a string with the first name of the user
     * @param lastName a string with the last name of the user
     */
    public Employee(String password, String username, String firstName, String lastName, String email) {
        super(password, username, firstName, lastName, email,"Employee");
    }
    public Employee( String username) {
        super("", username, "", "", "","Employee");
    }

    public Employee() {};


    //This method will be implemented when the Employee functionallity is added.


    /**
     * Create a new Clinic.
     */
    public void createClinic(String name) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
