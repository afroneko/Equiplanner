package com.lisa.equiplanner.Models;

public class Rider extends Person{
    private Boolean hasLeaseHorse;

    public Rider(int personId, String firstName, String lastName, String dateOfBirth, String email, String phonenumber, Address address, Boolean hasLeaseHorse) {
        super(personId, firstName, lastName, dateOfBirth, email, phonenumber, address);
        this.hasLeaseHorse = hasLeaseHorse;
    }

    // Getters
    public Boolean isHasLeaseHorse() {
        return hasLeaseHorse;
    }
}
