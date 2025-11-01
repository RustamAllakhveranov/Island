package com.javarush.projects.model.utilities;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.exceptions.InvalidWeightException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.organisms.Organism;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Идеально!
//Синхронизировать не нужно: методы validatePosition/isPositionValid - статические, данные не изменяются
//Логирование не требуется: оно происходит в вызывающих методах и классах
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    private Validator() {
    }

    public static void validatePosition(int positionX, int positionY) throws InvalidPositionException {
        if (!isPositionValid(positionX, positionY)) {
            String message = String.format("Invalid position: [%d][%d]", positionX, positionY);
            LOGGER.error(message);
            throw new InvalidPositionException(message);
        }
    }

    public static boolean isPositionValid(int positionX, int positionY) {
        return positionX >= 0
                && positionX < Island.WIDTH
                && positionY >= 0
                && positionY < Island.HEIGHT;
    }

    public static void validateWeight(Double weight) {
        if (weight < 0) {
            String message = String.format("Weight cannot be negative: %d", weight);
            LOGGER.error(message);
            throw new InvalidWeightException(message);
        }
    }
}