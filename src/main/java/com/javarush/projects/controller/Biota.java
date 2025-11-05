package com.javarush.projects.controller;

import com.javarush.projects.model.organisms.Organism;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Biota {
    private static final Logger LOGGER = LoggerFactory.getLogger(Biota.class.getName());
    private final List<Thread> organisms = new ArrayList<>();

    void addOrganism(Organism organism) {
        organisms.add(new Thread(organism));
    }

    public void launchBiota(long seconds) {
        LOGGER.trace("Симуляция запущена на {} секунд.", seconds);
        for (Thread organismThread : organisms) {
            organismThread.start();
        }
        try {
            Thread.sleep(1000 * seconds);
            for (Thread organismThread : organisms) {
                organismThread.interrupt();
            }
            LOGGER.trace("Симуляция остановлена!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
