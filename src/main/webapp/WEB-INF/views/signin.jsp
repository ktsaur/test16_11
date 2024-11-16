<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login page</title>
</head>

<body>
  <c:if test="${not empty error}">
    <div>${error}</div>
  </c:if>
<form method="post" action="/login">
  Login:
  <input type="text" name="login">
  <br>
  Password:
  <input type="password" name="password">
  <br>
  <input type="submit" value="signin">
  <br>
  <br>

  <a href="<c:url value="/registration"/>"> Еще не зарегистрированы? Зарегистрироваться</a>

</form>
</body>

</html>