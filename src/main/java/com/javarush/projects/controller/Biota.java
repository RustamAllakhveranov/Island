package com.javarush.projects.controller;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Organism;
import com.javarush.projects.model.organisms.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Biota {
    private List<Organism> organisms = new ArrayList<>();

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

//    public List<Organism> getOrganisms() {
//        return organisms;
//    }

    public void disperseOrganisms() {
        Sector sector;
        Organism organism;
        while (!organisms.isEmpty()) {
            organism = organisms.getFirst();
            sector = getRandomSector();
            if (organism instanceof Animal animal) {
                if (sector.addAnimal(animal))
                    organisms.remove(organism);
            } else if (organism instanceof Plant plant) {
                if (sector.addPlant(plant))
                    organisms.remove(organism);
            }
        }
    }

    public int randomX() {
        return ThreadLocalRandom.current().nextInt(Island.WIDTH);
    }

    public int randomY() {
        return ThreadLocalRandom.current().nextInt(Island.HEIGHT);
    }

    private Sector getRandomSector() {
        return Island.getInstance().getSector(randomX(), randomY());
    }
}
