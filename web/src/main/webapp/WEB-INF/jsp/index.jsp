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
    <div id="formContainer"></div>
    <div id="resultContainer">
        <div id="result"></div>
        <div id="resultProgress" style="display:none">
            <img src="/images/ajax-loader.gif"/>
        </div>
    </div>

</div>
</body>
</html>
