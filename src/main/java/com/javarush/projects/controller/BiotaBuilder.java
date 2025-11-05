package com.javarush.projects.controller;

import com.javarush.projects.model.organisms.Animal;

public interface BiotaBuilder {
    StandardBiotaBuilder addAnimals(Class<? extends Animal> animalClass, int count);

    StandardBiotaBuilder addPlants(int count);
}
