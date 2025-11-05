package com.javarush.projects;

import com.javarush.projects.controller.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.trace("Main started!");
        GameManager gameManager = new GameManager();
        gameManager.startSimulation(1);
    }
}