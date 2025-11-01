package com.javarush.projects.model.organisms;

import com.javarush.projects.model.island.Sector;

public abstract class Herbivore extends Animal implements CanEatPlant {
    protected Herbivore(Sector startPosition) {
        super(startPosition);
    }

    @Override
    protected synchronized void tryToSatisfyHunger() {
        graze();
    }

    public void graze() {
        Plant plant = currentLocation.getPlants().stream()
                .findFirst()
                .orElse(null);
        if (plant != null) {
            LOGGER.debug("{} нашел еду: {}", this, plant);
            tryToEatPlant(plant);
        }
    }


    @Override
    public void tryToEatPlant(Plant plant) {
        LOGGER.debug("{} trying to eat {} in {}", this, plant, currentLocation);
        synchronized (this) {
            double foodEaten = plant.decreaseBodyWeight(getRequiredFoodWeight());
            if (foodEaten > 0) {
                increaseSaturation(foodEaten);
                LOGGER.debug(
                        "{} съел {} кг {}. Насыщение: {}. Вес остатков растения: {}",
                        this, foodEaten, plant, getCurrentSaturation(), plant.getBodyWeight()
                );
                if (plant.getBodyWeight() == 0) {
                    plant.die();
                }
            } else {
                LOGGER.debug("{} не успел съесть {}", this, plant);
            }
        }
    }

}