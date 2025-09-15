package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.InvalidPositionException;
import com.javarush.projects.model.island.Island;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AnimalTest {
    @BeforeAll
    static void initIsland() {
        Island island = Island.getInstance();
    }

    @Test
    void navigate_Does_Not_Throw_ArrayIndexOutOfBoundsException() {
        Wolf animal = new Wolf(0, 0);
        for (int i = 0; i < 10000; i++) {
            animal.navigate();
        }
        assertDoesNotThrow(() -> new ArrayIndexOutOfBoundsException());
    }
}