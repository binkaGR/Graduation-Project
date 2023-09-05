package com.example.parkingserver;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ParkingSpotReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private int ParkingSpotId;
    private Time StartReservation;
    private Time EndtReservation;
    private String NumberCar;

    public void SetId(Long _id) {
        this.Id = _id;
    }

    public Long GetId() {
        return this.Id;
    }

    public void SetParkingSpotId(int _ParkingspotId) {
        this.ParkingSpotId = _ParkingspotId;
    }

    public int GetParkingSpotId() {
        return this.ParkingSpotId;
    }

    public void SetStartReservation(Time _StartReservationTime) {
        this.StartReservation = _StartReservationTime;
    }

    public Time GetStartReservation() {
        return this.StartReservation;
    }

    public void SetEndReservation(Time _EndReservationTime) {
        this.EndtReservation = _EndReservationTime;
    }

    public Time GetEndReservation() {
        return this.EndtReservation;
    }

    public String GetCarNumber() {
        return this.NumberCar;
    }

    public void SetCarNumber(String _CarNumber) {
        this.NumberCar = _CarNumber;
    }
}
