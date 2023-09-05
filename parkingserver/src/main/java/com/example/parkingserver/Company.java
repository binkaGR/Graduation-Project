package com.example.parkingserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCompany;
    private String company_name;
    private String Address;

    public Long GetIdCompany() {
        return this.IdCompany;
    }

    public void SetIdCompany(Long _IdCompany) {
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
