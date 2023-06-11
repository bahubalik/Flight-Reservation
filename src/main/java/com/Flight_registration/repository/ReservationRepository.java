package com.Flight_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Flight_registration.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Reservation findReservationByFlightId(Long flightId);

}
