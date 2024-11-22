<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Weather Page</title>
</head>
<body>
<h1>Weather Information</h1>
<form action="/main" method="post">
  <input type="text" name="cityName" placeholder="Enter city name" required>
  <input type="submit" value="Get Weather">
</form>

  <h2>Weather in ${cityName}</h2>
  <c:if test="${not empty error}">
    <p>${error}</p>
  </c:if>
  <c:if test="${empty error}">
    <p>Description: ${description}</p>
    <p>Tmperature: ${temperature}°C</p>
  </c:if>
</body>
</html>
