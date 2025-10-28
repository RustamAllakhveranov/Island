package com.javarush.projects.model.metadata;

public enum AnimalSpecies {
    BEAR(500, 5, 2, 80),
    BOA(15, 30, 1, 3),
    BOAR(400, 50, 2, 50),
    BUFFALO(700, 10, 3, 100),
    CATERPILLAR(0.01, 1000, 0, 0),
    DEER(300, 20, 4, 50),
    DUCK(1, 200, 4, 0.15),
    EAGLE(6, 20, 3, 1),
    FOX(8, 30, 2, 2),
    GOAT(60, 140, 3, 10),
    HORSE(400, 20, 4, 60),
    MOUSE(0.05, 500, 1, 0.01),
    RABBIT(20, 150, 2, 0.45),
    SHEEP(70, 140, 3, 15),
    WOLF(50, 30, 3, 8);
    private final double weight;
    private final int maxSpeciesOnCell;
    private final int speed;
    private final double maxSaturation;

    AnimalSpecies(double weight, int maxSpeciesOnCell, int speed, double maxSaturation) {

        this.weight = weight;
        this.maxSpeciesOnCell = maxSpeciesOnCell;
        this.speed = speed;
        this.maxSaturation = maxSaturation;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxSpeciesOnCell() {
        return maxSpeciesOnCell;
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
}