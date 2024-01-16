package com.example.servletserverexamples.users.servlets;

import com.example.servletserverexamples.users.logic.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class Delete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = JsonUtils.readJson(gson, request);
        request.setCharacterEncoding("UTF-8");

        int id = json.get("id").getAsInt();
        JsonObject jsonResponse = new JsonObject();
        if (model.isUserWithIdInMap(id)) {
            model.deleteUser(id);
            jsonResponse.addProperty("message", String.format("Пользователь с id=%d удален.", id));
        } else {
            jsonResponse.addProperty("message", String.format("Не удалось найти пользователя c id=%d.", id));
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(jsonResponse);
    }
}
