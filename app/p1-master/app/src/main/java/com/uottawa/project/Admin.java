package com.uottawa.project;

public class Admin extends Account {

    /**
     * Creates a new Admin account.
     * @param password a string with the password
     * @param username a string with the username
     * @param firstName a string with the first name of the user
     * @param lastName a string with the last name of the user
     */
    public Admin(String password, String username, String firstName, String lastName, String email) {
        super(password, username, firstName, lastName, email,"Admin");
    }

    public Admin(Account user){
       super (user.getPassword(), user.getUsername(),user.getFirstName(), user.getLastName(),user.getEmail(),"Admin");
    }
    //These methods will be implemented when Admin support is added.

    /**
     * Deletes the given clinic.
     */
    public void deleteClinic(Clinic clinic) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
