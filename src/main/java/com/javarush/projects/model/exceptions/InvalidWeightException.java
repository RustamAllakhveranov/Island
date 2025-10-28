package com.javarush.projects.model.exceptions;

public class InvalidWeightException extends RuntimeException {
    public InvalidWeightException(String message) {
        super(message);
    }
}
