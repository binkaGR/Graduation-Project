package com.example.parkingspotapp;

import java.io.Serializable;
import java.sql.Time;

public class ParkingReservationInformationJson implements Serializable {
    private String ParkingSpotName;
    private String Location;
    private String StartReservation;
    private String EndtReservation;

    public void setParkingSpotName(String parkingSpotName) {
        ParkingSpotName = parkingSpotName;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setStartReservation(String startReservation) {
        StartReservation = startReservation;
    }

    public void setEndtReservation(String endtReservation) {
        EndtReservation = endtReservation;
    }

    public String getParkingSpotName()
    {
        return this.ParkingSpotName;
    }

    public String getLocation() {
        return Location;
    }

    public String getStartReservation() {
        return StartReservation;
    }

    public String getEndtReservation() {
        return EndtReservation;
    }
}
