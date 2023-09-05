package com.example.parkingserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;
    private String FirstName;
    private String LastName;
    private String EmailAddress;
    private int CompanyId;

    public Long GetUserId() {
        return this.UserId;
    }

    public void SetUserId(Long _UserId) {
        this.UserId = _UserId;
    }

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

    public int GetCompanyId() {
        return this.CompanyId;
    }

    public void SetCompanyId(int _CompanyId) {
        this.CompanyId = _CompanyId;
    }
}
