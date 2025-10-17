package com.javarush.projects.model.utilities;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.island.Island;

//Идеально!
//Синхронизировать не нужно: методы validatePosition/isPositionValid - статические, данные не изменяются
//Логирование не требуется: оно происходит в вызывающих методах и классах
public class Validator {
    private Validator() {
    }

    public static void validatePosition(int positionX, int positionY) throws InvalidPositionException {
        if (!isPositionValid(positionX, positionY)) {
            throw new InvalidPositionException(String.format("Invalid position: [%d][%d]", positionX, positionY)
            );
        }
    }

    public static boolean isPositionValid(int positionX, int positionY) {
        return positionX >= 0
                && positionX < Island.WIDTH
                && positionY >= 0
                && positionY < Island.HEIGHT;
    }
}