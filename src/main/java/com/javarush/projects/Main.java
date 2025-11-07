package com.javarush.projects;

import com.javarush.projects.controller.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());
    public static final AtomicLong GLOBAL_ACTION_COUNTER = new AtomicLong(0);

    public static void main(String[] args) {
        LOGGER.trace("Main started!");
        GameManager gameManager = new GameManager();
        gameManager.startSimulation(1);
    }
}