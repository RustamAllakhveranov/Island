package com.javarush.projects.controller;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.organisms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class.getName());
    private List<Thread> threadList;

    public GameManager() {
        threadList = new ArrayList<>();
    }

    public void startSimulation(long seconds) {
        LOGGER.debug("Симуляция запущена!");
        addPlants();
        addRabbits(10);
        addWolves(2);
        for (Thread thread : threadList)
            thread.start();
        try {
            Thread.sleep(1000 * seconds);
            for (Thread thread : threadList)
                thread.interrupt();
            LOGGER.info("Симуляция остановлена!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void addRabbits(int quantity) {
        for (int i = 0; i < quantity; i++) {
            threadList.add(new Thread(new Rabbit(0, i)));
            threadList.add(new Thread(new Rabbit(0, i)));
        }

    }

    private void addPlants() {
        for (int positionX = 0; positionX < Island.WIDTH; positionX++) {
            Plant plant;
            for (int positionY = 0; positionY < Island.HEIGHT; positionY++) {
                plant = new Plant(positionX, positionY);
                new Thread(plant).start();
            }
        }
    }

    private void addWolves(int quantity) {
        for (int i = 0; i < quantity; i++)
            threadList.add(new Thread(new Wolf(0, i)));
    }


}