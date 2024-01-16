package com.example.servletserverexamples.users.servlets;

import com.example.servletserverexamples.JsonUtils;
import com.example.servletserverexamples.users.logic.Model;
import com.example.servletserverexamples.users.logic.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class Get extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonPrimitiveValue = request.getParameter("id");
        if (jsonPrimitiveValue != null && !jsonPrimitiveValue.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            if (id == 0) {
                pw.print("<html>" +
                        "<h3>Доступные пользователи:</h3>" +
                        "ID пользователя: " +
                        "<ul>");
                for (Map.Entry<Integer, User> entry : model.getAllUsers().entrySet()) {
                    pw.print("<li>" + entry.getKey() + "</li>" +
                            "<ul>" +
                            "<li>" + entry.getValue().getName() + "</li>" +
                            "<li>" + entry.getValue().getSurname() + "</li>" +
                            "<li>" + entry.getValue().getSalary() + "</li>" +
                            "</ul>");
                }
                pw.print("</ul>" +
                        "<a href=\"index.jsp\">Домой</a>" +
                        "</html>");
            } else if (id > 0) {
                if (id > model.getAllUsers().size()) {
                    pw.println("<html>" +
                            "<h3>Такого пользователя нет</h3>" +
                            "<a href=\"index.jsp\">Домой</a>" +
                            "</html>");
                } else {
                    pw.print("<html>" +
                            "<h3>Запрошенный пользователь</h3>" + "<br/>" +
                            "Имя: " + model.getAllUsers().get(id).getName() + "<br/>" +
                            "Фамилия: " + model.getAllUsers().get(id).getSurname() + "<br/>" +
                            "Зарплата: " + model.getAllUsers().get(id).getSalary() + "<br/>" +
                            "<a href=\"index.jsp\">Домой</a>" +
                            "</html>");
                }
            } else {
                pw.println("<html>" +
                        "<h3>ID должен быть больше нуля! </h3>" +
                        "<a href=\"index.jsp\">Домой</a>" +
                        "</html>");
            }
        } else {
            JsonObject json = JsonUtils.readJson(gson, request);
            request.setCharacterEncoding("UTF-8");
            int id = json.get("id").getAsInt();

            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            if (id == 0) {
                pw.print(gson.toJson(model.getAllUsers()));
            } else if (model.isUserWithIdInMap(id)) {
                pw.print(gson.toJson(model.getUser(id)));
            } else {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("message", String.format("Не удалось найти пользователя c id=%d.", id));
                pw.print(jsonResponse);
            }
        }
    }
}
