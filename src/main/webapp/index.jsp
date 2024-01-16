<%@page import="com.example.servletserverexamples.users.logic.Model" %>
<%@ page import="java.io.PrintWriter" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Домашняя работа по работе с пользователем</h1>
Введите ID пользователя (0 - для вывода всего списка пользователей)
<br/>
Доступно: <%
    Model model = Model.getInstance();
    out.print(model.getAllUsers().size());
%>
<form method="get" action="get">
    <label>ID
        <input type="text" name="id"><br/>
    </label>
    <button type="submit">Поиск</button>
</form>


<a href="addUser.html">Создать нового пользователя</a>
</body>
</html>