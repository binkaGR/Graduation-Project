package com.example.parkingserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Parking_spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdParkingSpot;
    private String ParkingSpotName;
    private String StatusParkingSpot;
    private int CompanyId;

    public Long GetIdParkingSpot() {
        return this.IdParkingSpot;
    }

    public void SetIdParkingSpot(Long _IdParkingSpot) {
        this.IdParkingSpot = _IdParkingSpot;
    }

    public String GetParkingSpotName() {
        return this.ParkingSpotName;
    }

    public void SetParkingSpotName(String _ParkingSpot) {
        this.ParkingSpotName = _ParkingSpot;
    }

    public String GetStatusParkingSpot() {
        return this.StatusParkingSpot;
    }

    public void SetStatusParkingSpot(String _StatusParkingSpot) {
        this.StatusParkingSpot = _StatusParkingSpot;
    }

    public int GetCompanyId() {
        return this.CompanyId;

    }

    public void SetCompanyId(int _CompanyId) {
        this.CompanyId = _CompanyId;
    }
}
