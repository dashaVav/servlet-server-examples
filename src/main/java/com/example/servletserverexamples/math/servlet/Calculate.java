package com.example.servletserverexamples.math.servlet;

import com.example.servletserverexamples.math.logic.Model;
import com.example.servletserverexamples.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calculate")
public class Calculate extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = JsonUtils.readJson(gson, request);
        request.setCharacterEncoding("UTF-8");

        double a = json.get("a").getAsDouble();
        double b = json.get("b").getAsDouble();
        String math = json.get("math").getAsString();

        JsonObject jsonResponse = new JsonObject();
        try {
            double result = model.calculate(a, b, math);
            jsonResponse.addProperty("result", result);

        } catch (IllegalAccessException e) {
            jsonResponse.addProperty("result", e.getMessage());
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(jsonResponse);
    }
}
