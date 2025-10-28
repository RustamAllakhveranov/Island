package com.javarush.projects.model.organisms;

import java.util.List;

public abstract class Herbivore extends Animal implements CanEatPlant {
    protected Herbivore(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tryToEatPlant(Plant plant) {
        LOGGER.trace("{} trying to eat plant in {}", this, currentLocation);
        synchronized (this) {
            double requiredFoodWeight = getRequiredFoodWeight();
            double foodWeightToAcquire;
            double plantBodyWeight = plant.getBodyWeight();
            if (requiredFoodWeight >= plantBodyWeight) {
                foodWeightToAcquire = plantBodyWeight;
            } else {
                foodWeightToAcquire = requiredFoodWeight;
            }
            LOGGER.debug("{} начинает есть {}", this, plant);
            eatOrganism(plant, foodWeightToAcquire);
        }
    }

    private synchronized void eatOrganism(Organism plant, double foodWeightToAcquire) {
        if(plant.getBodyWeight() <= 0){
            LOGGER.debug("{} не успел на застолье!", this);
            return;
        }
        LOGGER.debug("foodWeightToAcquire: {} for {}", foodWeightToAcquire, this);
        plant.decreaseBodyWeight(foodWeightToAcquire);
        increaseSaturation(foodWeightToAcquire);
        LOGGER.debug(
                "{} съел {} кг {}. Насыщение: {}. Вес остатков растения: {}",
                this, foodWeightToAcquire, plant, getCurrentSaturation(), plant.getBodyWeight()
        );
    }

    @Override
    protected Organism findFood() {
        synchronized (this) {
            List<Plant> plantList = currentLocation.getPlants();
            if (plantList.isEmpty()) {
                LOGGER.debug("{} didn't find food at {}", this, currentLocation);
                return null;
            }
            LOGGER.trace("{} found plant!", this);
            return plantList.getFirst();
        }
    }

    @Override
    protected synchronized void tryToSatisfyHunger() {
        Plant plant = (Plant) findFood();
        if (plant != null) {
            tryToEatPlant(plant);
        }
    }
}