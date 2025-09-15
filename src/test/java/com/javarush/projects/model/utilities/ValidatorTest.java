package com.javarush.projects.model.utilities;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.island.Island;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    private static Island island;

    @BeforeAll
    static void initIsland() {
        island = Island.getInstance();
    }

    @Test
    void validatePosition() {
        Executable e = () -> Validator.validatePosition(-3, -4);
        assertThrows(InvalidPositionException.class, e);
    }

    @Test
    void isPositionValid() {
        boolean a = Validator.isPositionValid(-1, -1);
        boolean b = Validator.isPositionValid(Island.WIDTH, island.HEIGHT);
        boolean c = Validator.isPositionValid(Island.WIDTH - 2, Island.HEIGHT - 1);
        Assertions.assertFalse(a);
        Assertions.assertFalse(b);
        Assertions.assertTrue(c);
    }
}