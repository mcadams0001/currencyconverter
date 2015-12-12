<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="/currency-web/register.html" method="post">
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="userName" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Repeat Password:</td>
            <td><input type="password" name="repeatPassword" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Email address:</td>
            <td><input type="text" name="email" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Street:</td>
            <td><input type="text" name="street" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>City:</td>
            <td><input type="text" name="city" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Post Code:</td>
            <td><input type="text" name="postcode" value="" maxlength="100"/></td>
        </tr>
        <tr>
            <td>Country</td>
            <td>
                <select name="country">
                <c:forEach items="${countries}" var="country">
                    <option id="${country.code}">${country.name}</option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Register"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
