package com.example.parkingspotapp;

import java.io.Serializable;

public class UserInformation implements Serializable {

    private String FirstName;
    private String LastName;
    private String EmailAddress;



    public String GetFirstname() {
        return this.FirstName;
    }

    public void SetFirstname(String _Firstname) {
        this.FirstName = _Firstname;
    }

    public String GetLastname() {
        return this.LastName;
    }

    public void SetLastname(String _Lastname) {
        this.LastName = _Lastname;
    }

    public String GetEmail() {
        return this.EmailAddress;
    }

    public void SetEmail(String _Email) {
        this.EmailAddress = _Email;
    }

}
