package com.example.parkingserver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotReservationReprository extends JpaRepository<ParkingSpotReservation, Integer> {

}
