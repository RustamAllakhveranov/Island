package com.javarush.projects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    public void navigate() {
        Animal animal = new Animal(3, 4);
        for (int i = 0; i < 10000; i++) {
            animal.navigate();
        }
        assertDoesNotThrow(() -> new ArrayIndexOutOfBoundsException());
    }
}