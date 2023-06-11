<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Flight Search Results</title>
</head>
<body>
    <h1>Selected Flights</h1>
    <form action="selectFlight" method="post">
     <table border ='1'>
            <tr>
                <th>Flight Number</th>
                <th>Operating Airlines</th>
                <th>Departure City</th>
                <th>Arrival City</th>
                <th>Date of Departure</th>
                <th>Estimated Departure Time</th>
                <th>Select Flight</th>
            </tr>
            <c:forEach var="flight" items="${flights}">
                <tr>
                    <td>${flight.flightNumber}</td>
                    <td>${flight.operatingAirlines}</td>
                    <td>${flight.departureCity}</td>
                    <td>${flight.arrivalCity}</td>
                    <td>${flight.dateOfDeparture}</td>
                    <td>${flight.estimatedDepartureTime}</td>
                    <td><a href="passengerDetails?flightId=${flight.id}">Select</a></td>
                </tr>
            </c:forEach>
    </table>
    </form>
    
   
</body>
</html>
