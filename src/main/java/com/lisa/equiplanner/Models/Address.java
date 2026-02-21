package com.lisa.equiplanner.Models;

public class Address {
    private int addressID;
    private String zipCode;
    private int houseNo;
    private String suffix;
    private String street;
    private String city;
    private String country;

    public Address(int addressID, String zipCode, int houseNo, String suffix, String street, String city, String country){
        this.zipCode = zipCode;
        this.houseNo = houseNo;
        this.suffix = suffix;
        this.street = street;
        this.city = city;
        this.country = country;
        if (addressID==-1){
//            DBConnection.addressRepo.findAddressID(this);
        } else {
            this.addressID = addressID;
        }

    }

    /*The lines below are to return the requested data*/
    public Address(String zipCode, int houseNo, String suffix, String street, String city, String country){
        this(-1,zipCode,houseNo,suffix,street,city,country);
    }

    // Getters and setters
    public void setAddressID(int addressID){
        this.addressID = addressID;
    }

    public int getAddressID(){
        return this.addressID;
    }

    public String getZipCode(){
        return this.zipCode;
    }

    public int getHouseNumber(){
        return this.houseNo;
    }

    public String getSuffix(){
        return this.suffix;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getStreet(){
        return this.street;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return this.country;
    }
}
