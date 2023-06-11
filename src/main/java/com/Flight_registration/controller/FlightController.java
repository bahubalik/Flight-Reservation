package com.Flight_registration.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Flight_registration.entities.Flight;
import com.Flight_registration.entities.Passenger;
import com.Flight_registration.repository.FlightRepository;

@Controller
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @RequestMapping("/searchFlight")
    public String searchFlight(@RequestParam("departureCity") String departureCity,
                               @RequestParam("arrivalCity") String arrivalCity,
                               @RequestParam("dateOfDeparture") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfDeparture,
                               Model model) {
        List<Flight> flights = flightRepository.findByDepartureCityAndArrivalCityAndDateOfDeparture(departureCity, arrivalCity, dateOfDeparture);
        model.addAttribute("flights", flights);
        return "flightResults";
    }

    @RequestMapping("/passengerDetails")
    public String passengerDetails(@RequestParam("flightId") Long flightId, Model model) {
        // Retrieve the flight based on the flightId
        Optional<Flight> findById = flightRepository.findById(flightId);
        Flight flight = findById.orElse(null);
        if (flight == null) {
            // Handle flight not found error
            return "error";
        }
        model.addAttribute("flight", flight);
        model.addAttribute("passenger", new Passenger());
        return "selectedFlightsPage";
    }
}
