package com.example.parkingserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Username;
    private String Password;
    private String Access;

    public void SetId(Long _id) {
        this.Id = _id;
    }

    public Long GetId() {
        return this.Id;
    }

    public void SetUseraname(String _username) {
        this.Username = _username;
    }

    public String GetUsername() {
        return this.Username;
    }

    public void SetPassword(String _password) {
        this.Password = _password;
    }

    public String GetPassword() {
        return this.Password;
    }

    public void SetAccess(String _access) {
        this.Access = _access;
    }

    public String GetAccess() {
        return this.Access;
    }
}
