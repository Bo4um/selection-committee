<%@ page import="com.example.selectioncommittee.beans.Faculty" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP</title>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <style type="text/css">
        h1 {
            text-align: center;
        }
        .container {
            width: 800px;
            height: 800px;
            margin: 0 auto;
            background-color: #AFCDE7;
            border-radius: 10px;
        }

        .names {
            padding-top: 50px;
            display: flex;
            justify-content: space-around;
        }

        select, input {
            display: block;
            height: calc(2.25rem + 2px);
            padding: 0.375rem 0.75rem;
            font-family: inherit;
            font-size: 1rem;
            font-weight: 400;
            line-height: 1.5;
            color: #212529;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #bdbdbd;
            border-radius: 0.25rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }

        .second_layer {
            display: flex;
            margin-top: 50px;
            justify-content: space-around;
        }

        .third_layer {
            display: flex;
            margin-top: 50px;
            justify-content: space-around;
        }

        .fourth_layer {
            display: flex;
            margin-top: 50px;
            justify-content: space-around;
        }

        .btn {
            margin: 50px auto 0;
        }
    </style>
</head>
<body>
    <% List<Faculty> faculties = (List<Faculty>) request.getAttribute("faculties");
        request.setAttribute("faculties", faculties);%>
    <h1>Регистрация абитуриента</h1>
    <form method="post" action="MainServlet">
        <div class="container">
            <div class="names">
                <input name = "first_name" type="text" placeholder="Фамилия">
                <input name = "second_name" type="text" placeholder="Имя">
                <input name = "patronymic" type="text" placeholder="Отчество">
            </div>
            <div class="second_layer">
                <input type="date" id="start" name="birthday"
                       value="2022-06-24"
                       min="2005-06-24" max="2123-12-31">
                <select>
                    <c:forEach var="elem" items="#{faculties}">
                        <option value="${elem}}">${elem.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="third_layer">
                <input name = "first_subj" type="text" placeholder="Первый предмет">
                <input name = "second_subj" type="text" placeholder="Второй предмет">
            </div>
            <div class="fourth_layer">
                <input name = "third_subj" type="text" placeholder="Третий предмет">
                <input name = "average_score" type="text" placeholder="Средний балл">
            </div>
            <input class = "btn" type="submit" placeholder="Регистрация">
        </div>
    </form>
</body>
</html>