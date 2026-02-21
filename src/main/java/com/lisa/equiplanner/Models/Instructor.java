package com.lisa.equiplanner.Models;

public class Instructor extends Person{
    private Boolean doesTrailRides;

    public Instructor(int personId, String firstName, String lastName, String dateOfBirth, String email, String phonenumber, Address address, Boolean doesTrailRides) {
        super(personId, firstName, lastName, dateOfBirth, email, phonenumber, address);
        this.doesTrailRides = doesTrailRides;
    }

    // Getters
    public Boolean getDoesTrailRides() {
        return doesTrailRides;
    }
}
