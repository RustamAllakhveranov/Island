package com.javarush.projects.model.organisms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AnimalTest {

    @Test
    void navigate_Does_Not_Throw_Errors() {
        Wolf animal = new Wolf(0, 0);
        for (int i = 0; i < 10000; i++) {
            assertDoesNotThrow(
                    animal::navigate,
                    "Либо координаты за пределами поля, либо была попытка войти в исходный сектор"
            );
        }
    }
}