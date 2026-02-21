package com.lisa.equiplanner.Models;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phonenumber;
    private Address address;

    public Person(int personId, String firstName, String lastName, String dateOfBirth, String email, String phonenumber, Address address) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    // Getters
    public int getPersonId() {return personId;}
    public void setPersonId(int personId) {}
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

    @Override
    public String toString(){
        return this.firstName+ ": " + this.lastName;
    }
}
