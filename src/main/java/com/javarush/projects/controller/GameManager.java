package com.javarush.projects.controller;

import com.javarush.projects.model.organisms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class.getName());

    public void startSimulation(long seconds) {
        Biota biota = new StandardBiotaBuilder()
                .addPlants(10)
                .addAnimals(Wolf.class, 1)
                .addAnimals(Boar.class, 2)
                .addAnimals(Buffalo.class, 2)
                .addAnimals(Deer.class, 2)
                .addAnimals(Duck.class, 2)
                .addAnimals(Goat.class, 2)
                .addAnimals(Horse.class, 2)
                .addAnimals(Mouse.class, 2)
                .addAnimals(Rabbit.class, 2)
                .addAnimals(Sheep.class, 2)
                .getResult();
        biota.launchBiota(seconds);
    }
}