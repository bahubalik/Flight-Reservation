package com.Flight_registration.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
public class Reservation extends AbstractEntity{
	private boolean checkedIn;
	private int numberOfBags;
	@OneToOne
	private Passenger passenger;
	@OneToOne
	private Flight flight;
	public boolean isCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
	public int getNumberOfbags() {
		return numberOfBags;
	}
	public void setNumberOfbags(int numberOfbags) {
		this.numberOfBags = numberOfbags;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	
}
