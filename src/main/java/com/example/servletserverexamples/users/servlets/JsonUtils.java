package com.example.servletserverexamples.users.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;

public final class JsonUtils {
    public static JsonObject readJson(Gson gson, HttpServletRequest request) {
        StringBuilder jb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(String.valueOf(jb), JsonObject.class);
    }
}
