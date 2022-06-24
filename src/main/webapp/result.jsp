<%--
  Created by IntelliJ IDEA.
  User: dmitry
  Date: 24.06.2022
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <%
        String first_name = request.getAttribute("first_name").toString();
        String second_name = request.getAttribute("second_name").toString();
        String patronymic = request.getAttribute("patronymic").toString();
    %>

    <h1>Регистрация прошла успешно</h1>
    <p>Абитуриент <%=first_name%> <%=second_name%> <%=patronymic%> успешно зарегистрирован! Ожидайте окончания приемной комиссии.</p>
</body>
</html>
