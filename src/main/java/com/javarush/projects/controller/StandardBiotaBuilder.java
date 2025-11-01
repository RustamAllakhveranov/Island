package com.javarush.projects.controller;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.organisms.*;
import com.javarush.projects.model.utilities.Validator;

import java.util.concurrent.ThreadLocalRandom;

public class StandardBiotaBuilder implements BiotaBuilder {
    private Biota biota;

    public StandardBiotaBuilder() {
        reset();
    }

    private void reset() {
        this.biota = new Biota();
    }

    public Biota getResult() {
        return biota;
    }

    @Override
    public StandardBiotaBuilder addBears(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.BEAR);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Bear.class);
            biota.addOrganism(new Bear(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addBoas(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.BOA);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Boa.class);
            biota.addOrganism(new Boa(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addBoars(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.BOAR);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Boar.class);
            biota.addOrganism(new Boar(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addBuffalos(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.BUFFALO);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Buffalo.class);
            biota.addOrganism(new Buffalo(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addCaterpillars(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.CATERPILLAR);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(CaterPillar.class);
            biota.addOrganism(new CaterPillar(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addDeers(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.DEER);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Deer.class);
            biota.addOrganism(new Deer(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addDucks(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.DUCK);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Duck.class);
            biota.addOrganism(new Duck(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addEagles(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.EAGLE);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Eagle.class);
            biota.addOrganism(new Eagle(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addFoxes(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.FOX);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Fox.class);
            biota.addOrganism(new Fox(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addGoats(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.GOAT);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Goat.class);
            biota.addOrganism(new Goat(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addHorses(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.HORSE);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Horse.class);
            biota.addOrganism(new Horse(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addMouses(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.MOUSE);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Mouse.class);
            biota.addOrganism(new Mouse(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addPlants(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.PLANT);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForPlant();
            biota.addOrganism(new Plant(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addRabbits(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.RABBIT);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Rabbit.class);
            biota.addOrganism(new Rabbit(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addSheeps(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.SHEEP);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Sheep.class);
            biota.addOrganism(new Sheep(sector));
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addWolves(int count) {
        Validator.validateCountOfSpecies(count, OrganismSpecies.WOLF);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(Wolf.class);
            biota.addOrganism(new Wolf(sector));
        }
        return this;
    }

    private Sector getRandomSectorForAnimal(Class<? extends Animal> animalClass) {
        Sector sector;
        while (true) {
            sector = getRandomSector();
            if (sector.hasSpaceForAnimal(animalClass)) {
                return sector;
            }
        }
    }

    private Sector getRandomSectorForPlant() {
        Sector sector;
        while (true) {
            sector = getRandomSector();
            if (sector.hasSpaceForPlant()) {
                return sector;
            }
        }
    }

    private Sector getRandomSector() {
        return Island.getInstance().getSector(getRandomX(), getRandomY());
    }

    public int getRandomX() {
        return ThreadLocalRandom.current().nextInt(Island.WIDTH);
    }


    public int getRandomY() {
        return ThreadLocalRandom.current().nextInt(Island.HEIGHT);
    }
}