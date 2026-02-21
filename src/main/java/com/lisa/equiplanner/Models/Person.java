package com.lisa.equiplanner.Models;

public class Person {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phonenumber;
    private Address address;

    public Person(String firstName, String lastName, String dateOfBirth, String email, String phonenumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public int getAddressID() {
        return address.getAddressID();
    }

    public Address getAddress() {
        return address;
    }
}
