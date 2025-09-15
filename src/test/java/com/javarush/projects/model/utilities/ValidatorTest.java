package com.javarush.projects.model.utilities;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.island.Island;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    private static Island island;

    @BeforeAll
    static void initIsland() {
        island = Island.getInstance();
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
    void isPositionValid_ReturnTrue_WithValidCoordinates(){
        for (int x = 0; x < Island.HEIGHT; x++) {
            for (int y = 0; y < Island.WIDTH; y++) {
                boolean result = Validator.isPositionValid(x, y);
                Assertions.assertTrue(result);
            }
        }
    }
}