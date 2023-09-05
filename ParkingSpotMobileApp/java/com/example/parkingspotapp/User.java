package com.example.parkingspotapp;

import java.io.Serializable;

public class User implements Serializable {
    private String Username;
    private String Password;
    private String Access = "client";


    public void SetUsername(String _Username)
    {
        this.Username = _Username;
    }
    public  String GetUseraname()
    {
        return  this.Username;
    }
    public void SetUPassword(String _Password)
    {
        this.Password = _Password;
    }
    public  String GetPassword()
    {
        return  this.Password;
    }
    public String GetAccess(){ return this.Access; }
}
