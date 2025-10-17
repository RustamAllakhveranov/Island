package com.javarush.projects;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Rabbit;
import com.javarush.projects.model.organisms.Wolf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.debug("Симуляция запущена!");
        Island island = Island.getInstance();
        List<Thread> threadList = new ArrayList<>();
        threadList.add(new Thread(new Wolf(0, 0)));

        for (int i = 0; i < 3; i++)
            threadList.add(new Thread(new Rabbit(0, i)));
        for (Thread thread : threadList)
            thread.start();
        try {
            Thread.sleep(1000);
            for (Thread thread : threadList)
                thread.interrupt();
            LOGGER.info("Симуляция остановлена!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}