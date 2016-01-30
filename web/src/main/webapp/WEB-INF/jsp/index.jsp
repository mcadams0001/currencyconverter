<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency Converter</title>
    <%@include file="include.jspf" %>
</head>
<body>
<div class="container">
<%@include file="logout.jspf"%>
    <div id="error"></div>
    <form:form id="convertCurrencyForm" action="/convertCurrency.html" method="post">
        <form:input path="amount" cssClass="form-control"/>
        <form:select path="from" cssClass="form-control">
            <form:options items="${currencies}" itemValue="code" itemLabel="name"/>
        </form:select>
        <form:select path="to" cssClass="form-control">
            <form:options items="${currencies}" itemValue="code" itemLabel="name"/>
        </form:select>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
    <div id="result"></div>
</div>
</body>
</html>
