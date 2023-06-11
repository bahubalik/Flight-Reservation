<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmation Page</title>
</head>
<body>
    <h1>Confirmation Page</h1>
    
    <h2>Flight Details</h2>
    <p>Flight Number: ${flight.flightNumber}</p>
    <p>Operating Airlines: ${flight.operatingAirlines}</p>
    <p>Departure City: ${flight.departureCity}</p>
    <p>Arrival City: ${flight.arrivalCity}</p>
    <p>Date of Departure: ${flight.dateOfDeparture}</p>
    <p>Estimated Departure Time: ${flight.estimatedDepartureTime}</p>
    
    <h2>Passenger Details</h2>
    <p>Passenger Name: ${passenger.firstName} ${passenger.lastName}</p>
    <p>Email: ${passenger.email}</p>
    <p>Phone: ${passenger.phone}</p>
    
    <h2>Reservation Details</h2>
    <p>Checked In: ${reservation.checkedIn}</p>
    <p>Number of Bags: ${reservation.numberOfbags}</p>
    
    <h2>Confirmation Message</h2>
    <p>Thank you for your reservation. Your flight, passenger, and reservation details have been successfully saved.</p>
    
    <!-- Add more sections or information related to the confirmation as needed -->
    <a href="generateTicketPDF?flightId=${flight.id}&passengerId=${passenger.id}&reservationId=${reservation.id}">
    Download PDF Ticket
</a>
</body>
</html>
