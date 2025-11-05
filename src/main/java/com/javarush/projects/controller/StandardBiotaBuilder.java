package com.javarush.projects.controller;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.organisms.*;
import com.javarush.projects.model.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class StandardBiotaBuilder implements BiotaBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardBiotaBuilder.class.getName());
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
    public StandardBiotaBuilder addAnimals(Class<? extends Animal> animalClass, int count) {
        LOGGER.info("{} start count: {}", animalClass, count);
        OrganismSpecies species = OrganismSpecies.getSpeciesByClass(animalClass);
        Validator.validateSpeciesCount(count, species);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForAnimal(animalClass);
            Organism organism = createOrganism(species, sector);
            biota.addOrganism(organism);
        }
        return this;
    }

    @Override
    public StandardBiotaBuilder addPlants(int count) {
        LOGGER.info("Plant start count: {}", count);
        OrganismSpecies species = OrganismSpecies.getSpeciesByClass(Plant.class);
        Validator.validateSpeciesCount(count, species);
        for (int i = 0; i < count; i++) {
            Sector sector = getRandomSectorForPlant();
            biota.addOrganism(createOrganism(species, sector));
        }
        return this;
    }

    private Organism createOrganism(OrganismSpecies species, Sector sector) {
        return switch (species) {
            case BEAR -> new Bear(sector);
            case BOA -> new Boa(sector);
            case BOAR -> new Boar(sector);
            case BUFFALO -> new Buffalo(sector);
            case CATERPILLAR -> new CaterPillar(sector);
            case DEER -> new Deer(sector);
            case DUCK -> new Duck(sector);
            case EAGLE -> new Eagle(sector);
            case FOX -> new Fox(sector);
            case GOAT -> new Goat(sector);
            case HORSE -> new Horse(sector);
            case MOUSE -> new Mouse(sector);
            case PLANT -> new Plant(sector);
            case RABBIT -> new Rabbit(sector);
            case SHEEP -> new Sheep(sector);
            case WOLF -> new Wolf(sector);
        };
    }


    //    @Override
//    public StandardBiotaBuilder addBears(int count) {
//        LOGGER.info("Bear start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.BEAR);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Bear.class);
//            biota.addOrganism(new Bear(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addBoas(int count) {
//        LOGGER.info("Boa start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.BOA);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Boa.class);
//            biota.addOrganism(new Boa(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addBoars(int count) {
//        LOGGER.info("Boar start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.BOAR);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Boar.class);
//            biota.addOrganism(new Boar(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addBuffalos(int count) {
//        LOGGER.info("Buffalo start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.BUFFALO);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Buffalo.class);
//            biota.addOrganism(new Buffalo(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addCaterpillars(int count) {
//        LOGGER.info("Caterpillar start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.CATERPILLAR);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(CaterPillar.class);
//            biota.addOrganism(new CaterPillar(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addDeers(int count) {
//        LOGGER.info("Deer start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.DEER);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Deer.class);
//            biota.addOrganism(new Deer(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addDucks(int count) {
//        LOGGER.info("Duck start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.DUCK);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Duck.class);
//            biota.addOrganism(new Duck(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addEagles(int count) {
//        LOGGER.info("Eagle start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.EAGLE);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Eagle.class);
//            biota.addOrganism(new Eagle(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addFoxes(int count) {
//        LOGGER.info("Fox start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.FOX);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Fox.class);
//            biota.addOrganism(new Fox(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addGoats(int count) {
//        LOGGER.info("Goat start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.GOAT);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Goat.class);
//            biota.addOrganism(new Goat(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addHorses(int count) {
//        LOGGER.info("Horse start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.HORSE);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Horse.class);
//            biota.addOrganism(new Horse(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addMouses(int count) {
//        LOGGER.info("Mouse start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.MOUSE);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Mouse.class);
//            biota.addOrganism(new Mouse(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addRabbits(int count) {
//        LOGGER.info("Rabbit start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.RABBIT);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Rabbit.class);
//            biota.addOrganism(new Rabbit(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addSheeps(int count) {
//        LOGGER.info("Sheep start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.SHEEP);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Sheep.class);
//            biota.addOrganism(new Sheep(sector));
//        }
//        return this;
//    }
//
//    @Override
//    public StandardBiotaBuilder addWolves(int count) {
//        LOGGER.info("Wolf start count: {}", count);
//        Validator.validateSpeciesCount(count, OrganismSpecies.WOLF);
//        for (int i = 0; i < count; i++) {
//            Sector sector = getRandomSectorForAnimal(Wolf.class);
//            biota.addOrganism(new Wolf(sector));
//        }
//        return this;
//    }
    private Sector getRandomSectorForAnimal(Class<? extends Animal> animalClass) {
        Sector sector;
        while (true) {
            sector = getRandomSector();
            if (sector.hasSpaceForAnimal(animalClass)) {
                LOGGER.trace("Start position for {}: {}", animalClass, sector);
                return sector;
            }
        }
    }

    private Sector getRandomSectorForPlant() {
        Sector sector;
        while (true) {
            sector = getRandomSector();
            if (sector.hasSpaceForPlant()) {
                LOGGER.trace("Start position for plant: {}", sector);
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