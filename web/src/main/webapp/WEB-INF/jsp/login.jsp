<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body onload="document.loginForm.j_username.focus();">
<div id="formSection">
    <form name="loginForm" action="j_spring_security_check" method="POST">
        <table>
            <tr>
                <td>Username</td>
                <td><input type="text" name="j_username" max="50"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="j_password" max="20"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Login"/></td>
            </tr>
            <tr>
                <td colspan="2"><a href="register.html">Register</a></td>
            </tr>
        </table>
    </form>
    <c:if test="${error != null}" >
        <span class="error">${error}</span>
    </c:if>
</div>
</body>
</html>