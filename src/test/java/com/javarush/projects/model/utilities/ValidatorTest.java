package com.javarush.projects.model.utilities;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.exceptions.InvalidWeightException;
import com.javarush.projects.model.island.Island;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "-3, 0",
            "0, -1",
            "1000, 0",
            "0, 7409",
            "-4, -5",
            "1000, 2000",
    })
    void validatePosition_Throws_InvalidPositionException(int positionX, int positionY) {
        Executable e = () -> Validator.validatePosition(positionX, positionY);
        assertThrows(InvalidPositionException.class, e);
    }

    @ParameterizedTest
    @CsvSource({
            "-3, 0",
            "0, -1",
            "1000, 0",
            "0, 7409",
            "-4, -5",
            "1000, 2000",
    })
    void isPositionValid_ReturnFalse_WithInvalidCoordinates(int positionX, int positionY) {
        boolean result = Validator.isPositionValid(positionX, positionY);
        Assertions.assertFalse(result);
    }

    @Test
    void isPositionValid_ReturnTrue_WithValidCoordinates() {
        Random random = new Random();
        int x = random.nextInt(Island.HEIGHT);
        int y = random.nextInt(Island.WIDTH);
        boolean result = Validator.isPositionValid(x, y);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest(name = "{displayName} Тест {index}: {arguments}")
    @MethodSource("getRandomNegativeDouble")
    void validateWeight(Double weight) {
        Assertions.assertThrows(
                InvalidWeightException.class,
                () -> Validator.validateWeight(weight)
        );
    }

    static Stream<Double> getRandomNegativeDouble() {
        final Random random = new Random();
        return Stream.generate(() -> -Math.abs(random.nextDouble()) - Double.MIN_VALUE).limit(100);
    }
}