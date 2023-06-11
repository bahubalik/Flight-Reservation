<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Flight</title>
</head>
<body>
    <h1>Find Flight</h1>
    <form action="searchFlight" method="post">
        <label for="departureCity">Departure City:</label>
        <input type="text" id="departureCity" name="departureCity" required><br><br>

        <label for="arrivalCity">Arrival City:</label>
        <input type="text" id="arrivalCity" name="arrivalCity" required><br><br>

        <label for="dateOfDeparture">Date of Departure:</label>
        <input type="date" id="dateOfDeparture" name="dateOfDeparture" required><br><br>

        <input type="submit" value="Search">
    </form>
</body>
</html>
