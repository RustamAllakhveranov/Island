package com.javarush.projects.model.exceptions;

public class SameDestinationException extends RuntimeException {
    public SameDestinationException(String message) {
        super(message);
    }
}
