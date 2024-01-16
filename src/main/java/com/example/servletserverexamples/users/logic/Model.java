package com.example.servletserverexamples.users.logic;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    @Getter
    private static final Model instance = new Model();
    private final Map<Integer, User> model;

    private Model() {
        model = new HashMap<>();
    }

    public Map<Integer, User> getAllUsers() {
        return model;
    }

    public User getUser(int id) {
        return model.get(id);
    }

    public void addUser(User user, int id) {
        model.put(id, user);
    }

    public void deleteUser(int id) {
        model.remove(id);
    }

    public void updateUser(int id, User user) {
        model.put(id, user);
    }

    public boolean isUserWithIdInMap(int id) {
        return model.containsKey(id);
    }
}

