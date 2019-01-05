<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
    <title>Currency Converter</title>
    <%@include file="include.jspf" %>
</head>
<body onload="handleLoadCompleted()">
<div class="container">
    <div id="logoutContainer" style="display: block; float: right">
        <%@include file="logout.jspf"%>
    </div>
    <h1>Currency Converter</h1>
    <div id="formContainer" class="container"></div>
    <div id="resultContainer" style="padding-bottom: 20px"></div>
    <div id="historyContainer"></div>
</div>
<script type="application/javascript">
    function handleLoadCompleted() {
        currency.initialize();
    }
</script>
</body>
</html>
