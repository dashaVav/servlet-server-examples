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
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class Add extends HttpServlet {
    private final AtomicInteger counter = new AtomicInteger(1);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonPrimitiveValue = request.getParameter("name");
        if (jsonPrimitiveValue != null && !jsonPrimitiveValue.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("UTF-8");

            PrintWriter pw = response.getWriter();
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            double salary = Double.parseDouble(request.getParameter("salary"));

            User user = new User(name, surname, salary);
            model.addUser(user, counter.getAndIncrement());
            pw.print("<html>" +
                    "<h3>Пользователь " + name + " " + surname + " с зарплатой = " + salary + " успешно создан!</h3>" +
                    "<a href=\"addUser.html\">Создать нового пользователя </a><bt/>" +
                    "<a href=\"index.jsp\">Домой</a>" +
                    "</html>");
        } else {
            JsonObject json = JsonUtils.readJson(gson, request);
            request.setCharacterEncoding("UTF-8");

            String name = json.get("name").getAsString();
            String surname = json.get("surname").getAsString();
            double salary = json.get("salary").getAsDouble();

            User user = new User(name, surname, salary);
            int id = counter.getAndIncrement();
            model.addUser(user, id);

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Пользователь добавлен.");
            jsonResponse.addProperty("id", id);

            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(jsonResponse);
        }
    }

}
