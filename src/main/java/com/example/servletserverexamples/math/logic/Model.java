package com.example.servletserverexamples.math.logic;

import lombok.Getter;

public class Model {
    @Getter
    private static final Model instance = new Model();

    public double calculate(double a, double b, String math) throws IllegalAccessException {
        switch (math) {
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "-":
                return a - b;
            case "+":
                return a + b;
            case "^":
                return Math.pow(a, b);
            default:
                throw new IllegalAccessException("Неверная математическая операция.");
        }
    }
}
