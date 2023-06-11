<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Passenger Details</title>
</head>
<body>
    <h1>Passenger Details</h1>
    
    <h2>Selected Flight:</h2>
    <p>Flight Number: ${flight.flightNumber}</p>
    <p>Operating Airlines: ${flight.operatingAirlines}</p>
    <p>Departure City: ${flight.departureCity}</p>
    <p>Arrival City: ${flight.arrivalCity}</p>
    <p>Date of Departure: <fmt:formatDate value="${flight.dateOfDeparture}" pattern="yyyy-MM-dd" /></p>
    <p>Estimated Departure Time: ${flight.estimatedDepartureTime}</p>
    
    <h2>Passenger Information:</h2>
    <form method="post" action="savePassengerDetails">
        <input type="hidden" name="flightId" value="${flight.id}">
        
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName" required><br>
        
        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName" required><br>
        
        <label for="middleName">Middle Name:</label>
        <input type="text" name="middleName" id="middleName"><br>
        
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required><br>
        
        <label for="phone">Phone:</label>
        <input type="text" name="phone" id="phone" required><br>
        
        <h3>Card Details:</h3>
        <label for="cardNumber">Card Number:</label>
        <input type="text" name="cardNumber" id="cardNumber" required><br>
        
        <label for="cardHolderName">Cardholder Name:</label>
        <input type="text" name="cardHolderName" id="cardHolderName" required><br>
        
        <label for="expiryDate">Expiry Date:</label>
        <input type="text" name="expiryDate" id="expiryDate" required><br>
        
        <label for="cvv">CVV:</label>
        <input type="text" name="cvv" id="cvv" required><br>
        
        <input type="submit" value="Save Passenger">
    </form>
</body>
</html>
