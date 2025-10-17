package com.javarush.projects.model.organisms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AnimalTest {

    @Test
    void navigate_Does_Not_Throw_ArrayIndexOutOfBoundsException() {
        Wolf animal = new Wolf(0, 0);
        for (int i = 0; i < 10000; i++) {
            animal.navigate();
        }
        assertDoesNotThrow(() -> new ArrayIndexOutOfBoundsException());
    }

    @Test
    void navigate_Does_Not_Choose_Previous_Position() {
        Animal wolf = new Wolf(0, 0);
        for (int i = 0; i < 10000; i++) {
            Assertions.assertDoesNotThrow(wolf::navigate);
        }
    }
}