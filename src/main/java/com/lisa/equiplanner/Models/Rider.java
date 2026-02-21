package com.lisa.equiplanner.Models;

public class Rider extends Person{
    private Boolean hasLeaseHorse;

    public Rider(String firstName, String lastName, String dateOfBirth, String email, String phonenumber, Address address, Boolean hasLeaseHorse) {
        super(firstName, lastName, dateOfBirth, email, phonenumber, address);
        this.hasLeaseHorse = hasLeaseHorse;
    }

    public Boolean getHasLeaseHorse() {
        return hasLeaseHorse;
    }
}
