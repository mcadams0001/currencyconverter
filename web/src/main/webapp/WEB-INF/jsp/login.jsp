<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>Currency Converter</title>
    <%@include file="include.jspf" %>
</head>
<body onload="document.loginForm.j_username.focus();">
<div id="formSection" class="container">
    <form class="form-signin" name="loginForm" action="/j_spring_security_check" method="POST">
        <c:if test="${error != null}">
            <span class="error">${error}</span>
        </c:if>
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="userName">Username</label>
        <input type="text" id="userName" name="j_username" max="50" class="form-control" placeholder="Username"/>
        <label for="password">Password</label>
        <input type="password" id="password" name="j_password" max="20" class="form-control" placeholder="Password"/>
        <input type="submit" value="Login" class="btn btn-primary btn-block"/>
        <a href="register.html">Register</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>