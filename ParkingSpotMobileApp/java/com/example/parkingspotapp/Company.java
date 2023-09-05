package com.example.parkingspotapp;

import java.io.Serializable;

public class Company implements Serializable {
    private int IdCompany;
    private String company_name;
    private String Address;

    public int GetIdCompany() {
        return this.IdCompany;
    }

    public void SetIdCompany(int _IdCompany) {
        this.IdCompany = _IdCompany;
    }

    public String GetCompanyName() {
        return this.company_name;
    }

    public void SetCompanyName(String _CompanyName) {
        this.company_name = _CompanyName;
    }

    public String GetAddress() {
        return this.Address;
    }

    public void SetAddress(String _Address) {
        this.Address = _Address;
    }
}
