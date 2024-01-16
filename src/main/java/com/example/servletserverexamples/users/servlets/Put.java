package com.example.servletserverexamples.users.servlets;

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

@WebServlet(urlPatterns = "/put")
public class Put extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = JsonUtils.readJson(gson, request);
        request.setCharacterEncoding("UTF-8");

        int id = json.get("id").getAsInt();
        User user = gson.fromJson(json.get("user"), User.class);

        JsonObject jsonResponse = new JsonObject();
        if (model.isUserWithIdInMap(id)) {
            model.updateUser(id, user);
            jsonResponse.addProperty("message", String.format("Информация о пользователе с id=%d обновлена.", id));
        } else {
            jsonResponse.addProperty("message", String.format("Не удалось найти пользователя c id %d.", id));
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(jsonResponse);
    }
}
