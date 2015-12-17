<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
    <%@include file="include.jspf" %>
</head>
<body>
<h1>Registration Form</h1>
Please provide your details below:
<div id="formControl" style="width: 500px; padding-left: 100px;">
    <form:form action="/currency-web/register.html" method="post" commandName="command" htmlEscape="true">
        <form:errors/>
        <table>
            <tr>
                <td><label for="name">Username</label></td>
                <td><form:input path="name" maxlength="100"/></td>
                <td style="width: 100px"><form:errors path="name"/></td>
            </tr>
            <tr>
                <td><label for="password">Password</label></td>
                <td><form:password path="password" maxlength="100"/></td>
                <td><form:errors path="password"/></td>
            </tr>
            <tr>
                <td><label for="repeatPassword">Repeat Password</label></td>
                <td><form:password path="repeatPassword" value="" maxlength="100"/></td>
                <td><form:errors path="repeatPassword"/></td>
            </tr>
            <tr>
                <td><label for="email">Email address</label></td>
                <td><form:input path="email" value="" maxlength="100"/></td>
                <td><form:errors path="email"/></td>
            </tr>
            <tr>
                <td><label for="birthDate">Birth date</label></td>
                <td><form:input path="birthDate" value="" maxlength=""/></td>
                <td><form:errors path="birthDate"/></td>
            </tr>
            <tr>
                <td><label for="street">Street</label></td>
                <td><form:input path="street" value="" maxlength="100"/></td>
                <td><form:errors path="street"/></td>
            </tr>
            <tr>
                <td><label for="city">City</label></td>
                <td><form:input path="city" value="" maxlength="100"/></td>
                <td><form:errors path="city"/></td>
            </tr>
            <tr>
                <td><label for="postCode">Post code</label></td>
                <td><form:input path="postCode" value="" maxlength="100"/></td>
                <td><form:errors path="postCode"/></td>
            </tr>
            <tr>
                <td><label for="country">Country</label></td>
                <td>
                    <form:select path="country">
                        <form:option value="">-- Please Select --</form:option>
                        <form:options items="${countries}" itemValue="code" itemLabel="name"/>
                    </form:select>
                </td>
                <td><form:errors path="country"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input class="btn btn-default" type="submit" value="Register"/></td>
            </tr>
        </table>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#birthDate').datepicker("option", "dateFormat", "dd-M-yy");
    });
</script>
</body>
</html>
