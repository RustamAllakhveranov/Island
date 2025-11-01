package com.javarush.projects.model.metadata;

import com.javarush.projects.model.organisms.*;

public enum OrganismSpecies {
    BEAR(Bear.class, 500, 5, 2, 80),
    BOA(Boa.class, 15, 30, 1, 3),
    BOAR(Boar.class, 400, 50, 2, 50),
    BUFFALO(Buffalo.class, 700, 10, 3, 100),
    CATERPILLAR(CaterPillar.class, 0.01, 1000, 0, 0),
    DEER(Deer.class, 300, 20, 4, 50),
    DUCK(Duck.class, 1, 200, 4, 0.15),
    EAGLE(Eagle.class, 6, 20, 3, 1),
    FOX(Fox.class, 8, 30, 2, 2),
    GOAT(Goat.class, 60, 140, 3, 10),
    HORSE(Horse.class, 400, 20, 4, 60),
    MOUSE(Mouse.class, 0.05, 500, 1, 0.01),
    PLANT(Plant.class, 1, 20, 0, 0),
    RABBIT(Rabbit.class, 20, 150, 2, 0.45),
    SHEEP(Sheep.class, 70, 140, 3, 15),
    WOLF(Wolf.class, 50, 30, 3, 8);
    private final Class<? extends Organism> organismClass;
    private final double weight;
    private final int maxSpeciesOnSector;
    private final int speed;
    private final double maxSaturation;

    OrganismSpecies(Class<? extends Organism> organismClass, double weight, int maxSpeciesOnSector, int speed, double maxSaturation) {
        this.organismClass = organismClass;
        this.weight = weight;
        this.maxSpeciesOnSector = maxSpeciesOnSector;
        this.speed = speed;
        this.maxSaturation = maxSaturation;
    }

    public Class<? extends Organism> getOrganismClass() {
        return organismClass;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxSpeciesOnSector() {
        return maxSpeciesOnSector;
    }

    public int getSpeed() {
        return speed;
    }

    public double getMaxSaturation() {
        return maxSaturation;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getNormalName() {
        return toString().charAt(0) + toString().substring(1).toLowerCase();
    }

    public static OrganismSpecies getSpeciesByClass(Class<? extends Organism> organismClass){
        return OrganismSpecies.valueOf(organismClass.getSimpleName().toUpperCase());
    }
}