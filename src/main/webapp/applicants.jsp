<%@ page import="java.util.List" %>
<%@ page import="com.example.selectioncommittee.beans.Applicant" %><%--
  Created by IntelliJ IDEA.
  User: dmitry
  Date: 24.06.2022
  Time: 01:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <style type="text/css">
        h1 {
            text-align: center;
        }
        table {
            margin: 0 auto;
            font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
            font-size: 16px;
            border-collapse: collapse;
            text-align: center;
        }
        th {
            background: #AFCDE7;
            color: white;
            padding: 10px 20px;
        }
        th, td {
            border-style: solid;
            border-width: 0 1px 1px 0;
            border-color: white;
        }
        td {
            background: #D8E6F3;
        }
    </style>
</head>
<body>
    <%
        List<Applicant> applicants = (List<Applicant>) request.getAttribute("applicants");
        request.setAttribute("applicants", applicants);
    %>
    <h1>Список всех абитуриентов университета</h1>
    <table>
        <thead>
        <tr>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${applicants}">
            <tr>
                <td>${item.firstName}</td>
                <td>${item.secondName}</td>
                <td>${item.patronymic}</td>
                <td>${item.birthday}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
