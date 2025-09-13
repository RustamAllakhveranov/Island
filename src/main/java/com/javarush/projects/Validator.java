package com.javarush.projects;

public class Validator {
    public static boolean isPositionValid(int positionX, int positionY) {
        return positionX >= 0
                && positionX < Island.WIDTH
                && positionY >= 0
                && positionY < Island.HEIGHT;
    }
}
