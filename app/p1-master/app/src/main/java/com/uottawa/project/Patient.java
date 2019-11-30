package com.uottawa.project;

public class Patient extends Account {

    /**
     * Creates a Patient Account.
     * @param password a string with the password
     * @param username a string with the username
     * @param firstName a string with the first name of the user
     * @param lastName a string with the last name of the user
     */
    public Patient(String password, String username, String firstName, String lastName, String email) {
        super(password, username, firstName, lastName, email,"Patient");
    }


    //More will be added here when Patient abilities are implemented.


}
