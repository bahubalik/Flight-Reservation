package com.Flight_registration.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Flight_registration.entities.Flight;
import com.Flight_registration.entities.Passenger;
import com.Flight_registration.entities.Reservation;
import com.Flight_registration.repository.FlightRepository;
import com.Flight_registration.repository.PassengerRepository;
import com.Flight_registration.repository.ReservationRepository;
import com.Flight_registration.service.EmailService;
import com.Flight_registration.service.TicketPDFGenerator;



import java.io.File;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

@Controller
public class PassengerController {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketPDFGenerator ticketPDFGenerator;

    @RequestMapping("/savePassengerDetails")
    public String savePassengerDetails(@RequestParam("flightId") Long flightId,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("middleName") String middleName,
                                       @RequestParam("email") String email,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("cardNumber") String cardNumber,
                                       @RequestParam("cardHolderName") String cardHolderName,
                                       @RequestParam("expiryDate") String expiryDate,
                                       @RequestParam("cvv") String cvv,
                                       Model model) {
        // Retrieve the flight based on the flightId
        Flight flight = flightRepository.findById(flightId).orElse(null);

        // Process the passenger details
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setMiddleName(middleName);
        passenger.setEmail(email);
        passenger.setPhone(phone);

        // Save the passenger details to the database
        passengerRepository.save(passenger);

        // Create a new reservation and set the flight and passenger
        Reservation reservation = new Reservation();
        reservation.setCheckedIn(false);
        reservation.setNumberOfbags(0);
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Add the necessary attributes to the model for display in the confirmation page
        model.addAttribute("flight", flight);
        model.addAttribute("passenger", passenger);
        model.addAttribute("reservation", savedReservation);

        // Generate the ticket PDF
        File ticketFile = ticketPDFGenerator.generateTicketPDF(flight, passenger, savedReservation);

        // Send reservation confirmation email with the ticket PDF attached
        String subject = "Flight Reservation Confirmation";
        String text = "Thank you for your reservation. Your flight, passenger, and reservation details are as follows:\n"
                + "Flight Number: " + flight.getFlightNumber() + "\n"
                + "Operating Airlines: " + flight.getOperatingAirlines() + "\n"
                + "Departure City: " + flight.getDepartureCity() + "\n"
                + "Arrival City: " + flight.getArrivalCity() + "\n"
                + "Date of Departure: " + flight.getDateOfDeparture() + "\n"
                + "Estimated Departure Time: " + flight.getEstimatedDepartureTime() + "\n"
                + "\n"
                + "Passenger Name: " + passenger.getFirstName() + " " + passenger.getLastName() + "\n"
                + "Email: " + passenger.getEmail() + "\n"
                + "Phone: " + passenger.getPhone() + "\n"
                + "\n"
                + "Reservation Details:\n"
                + "Checked In: " + reservation.isCheckedIn() + "\n"
                + "Number of Bags: " + reservation.getNumberOfbags();

        emailService.sendReservationEmail(email, subject, text, ticketFile);

        return "confirmationPage";
    }
    @RequestMapping("/generateTicketPDF")
    public void generateTicketPDF(@RequestParam("flightId") Long flightId,
                                  @RequestParam("passengerId") Long passengerId,
                                  @RequestParam("reservationId") Long reservationId,
                                  HttpServletResponse response) {
        try {
            // Retrieve the flight, passenger, and reservation based on their respective IDs
            Optional<Flight> flightOptional = flightRepository.findById(flightId);
            Optional<Passenger> passengerOptional = passengerRepository.findById(passengerId);
            Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);

            if (flightOptional.isPresent() && passengerOptional.isPresent() && reservationOptional.isPresent()) {
                Flight flight = flightOptional.get();
                Passenger passenger = passengerOptional.get();
                Reservation reservation = reservationOptional.get();

                // Create a new PDF document
                PDDocument document = new PDDocument();

                // Create a new page
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                // Create a new content stream for writing to the page
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Set the font and font size for heading
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);

                // Set the initial y-position for heading
                float yPosition = page.getMediaBox().getHeight() - 50;

                // Write the heading to the PDF
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Flight Ticket");
                contentStream.endText();

                // Set the font and font size for table
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Set the initial y-position for the table
                yPosition -= 40;

                // Set the cell width and height
                final float tableWidth = page.getMediaBox().getWidth() - 100;
                final float yStart = yPosition;
                final float bottomMargin = 70;
                final float cellMargin = 10;
                final float fontSize = 12;
                final float tableTopY = yStart - fontSize;
                final float tableBottomY = page.getMediaBox().getLowerLeftY() + bottomMargin;
                final float tableHeight = yStart - bottomMargin - 2 * fontSize;
                final float cellWidth = tableWidth / 2f;
                final float boarder = 1;
                // Define table headers
                String[] headers = { "Flight Details", "Passenger Details" };

             // Define table data
                String[][] data = {
                    { "Flight Number:", flight.getFlightNumber() },
                    { "Operating Airlines:", flight.getOperatingAirlines() },
                    { "Departure City:", flight.getDepartureCity() },
                    { "Arrival City:", flight.getArrivalCity() },
                    { "Passenger Name:", passenger.getFirstName() + " " + passenger.getLastName() },
                    { "Email:", passenger.getEmail() },
                    { "Phone:", passenger.getPhone() },
                    { "Checked-In:", reservation.isCheckedIn() ? "Yes" : "No" },
                    { "Number of Bags:", Integer.toString(reservation.getNumberOfbags()) }
                };


                // Draw table headers
                float currentY = tableTopY;
                float cellX = 50;
                for (String header : headers) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(cellX, currentY);
                    contentStream.showText(header);
                    contentStream.endText();
                    cellX += cellWidth;
                }

                // Draw table data
                currentY -= fontSize;
                for (String[] row : data) {
                    cellX = 50;
                    for (String cell : row) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(cellX, currentY);
                        contentStream.showText(cell);
                        contentStream.endText();
                        cellX += cellWidth;
                    }
                    currentY -= fontSize;
                }

                // Close the content stream
                contentStream.close();

                // Set the response headers for PDF download
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"ticket.pdf\"");

                // Write the generated PDF to the response output stream
                document.save(response.getOutputStream());

                // Close the PDF document
                document.close();
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during PDF generation
            e.printStackTrace();
        }
    }


}

