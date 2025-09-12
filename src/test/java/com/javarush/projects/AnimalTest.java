package com.javarush.projects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @BeforeAll
    static void initIsland() {
        Island.createIsland();
    }

    @Test
    void navigate_Does_Not_Throw_ArrayIndexOutOfBoundsException() {
        Wolf animal = new Wolf(3, 4);
        for (int i = 0; i < 10000; i++) {
            animal.navigate();
        }
        assertDoesNotThrow(() -> new ArrayIndexOutOfBoundsException());
    }


}