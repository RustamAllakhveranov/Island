package com.javarush.projects.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class.getName());

    public void startSimulation(long seconds) {
        Biota biota = new StandardBiotaBuilder()
                .addPlants(100)
                .addWolves(4)
                .addRabbits(20)
                .getResult();
        biota.launchBiota(seconds);
    }
}