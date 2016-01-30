<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
    <%@include file="include.jspf" %>
</head>
<body>
<div id="formSection" class="container">
    <h1>Registration Form</h1>
    Please provide your details below:
    <form:form action="/register.html" method="post" commandName="command" htmlEscape="true">
        <form:errors cssClass="error"/>
        <table class="registrationTable">
            <tr>
                <td><label for="firstName">First name</label></td>
                <td width="300px"><form:input path="firstName" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="firstName" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name</label></td>
                <td><form:input path="lastName" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="lastName" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="name">Username</label></td>
                <td><form:input path="name" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="name" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="password">Password</label></td>
                <td><form:password path="password" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="repeatPassword">Repeat Password</label></td>
                <td><form:password path="repeatPassword" value="" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="repeatPassword" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="email">Email address</label></td>
                <td><form:input path="email" value="" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="email" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="birthDate">Birth date</label></td>
                <td><form:input path="birthDate" value="" maxlength="" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="birthDate" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="street">Street</label></td>
                <td><form:input path="street" value="" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="street" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="city">City</label></td>
                <td><form:input path="city" value="" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="city" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="postCode">Post code</label></td>
                <td><form:input path="postCode" value="" maxlength="100" htmlEscape="true" cssClass="form-control"/></td>
                <td><form:errors path="postCode" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="country">Country</label></td>
                <td>
                    <form:select path="country" cssClass="form-control">
                        <form:option value=""> -- Please Select -- </form:option>
                        <form:options items="${countries}" itemValue="code" itemLabel="name"/>
                    </form:select>
                </td>
                <td><form:errors path="country" cssClass="error"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input class="btn btn-primary" type="submit" value="Register"/>
                    &nbsp;
                    <input class="btn" type="button" value="Back" onclick="window.location='/index.html'"/>
                </td>
                <td></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        console.log("document ready");
        $('#birthDate').datepicker("option", "dateFormat", "dd-MMM-yyyy");
    });
</script>
</body>
</html>
