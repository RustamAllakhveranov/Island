package com.javarush.projects.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class.getName());

    public void startSimulation(long seconds) {
        Biota biota = new StandardBiotaBuilder()
                .addPlants(10)
                .addWolves(1)
                .addBuffalos(5)
                .addDeers(5)
                .addDucks(5)
                .addGoats(5)
                .addHorses(5)
                .addMouses(5)
                .addRabbits(5)
                .addSheeps(5)
                .getResult();
        biota.launchBiota(seconds);
    }
}