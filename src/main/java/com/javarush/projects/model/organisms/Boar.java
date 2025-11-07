package com.javarush.projects.model.organisms;

import com.javarush.projects.model.island.Sector;

public class Boar extends Predator implements CanEatPlant {
    public Boar(Sector startPosition) {
        super(startPosition);
    }

    @Override
    protected synchronized void tryToSatisfyHunger() {
        super.tryToSatisfyHunger();
        graze();
    }

    @Override
    public void graze() {
        Plant plant = currentLocation.findPlant();
        if (plant == null || plant.isDead()) return;
        LOGGER.debug("{} нашел еду: {}", this, plant);
        double requiredFoodWeight = getRequiredFoodWeight();
        double foodEaten;
        synchronized (plant) {
            LOGGER.debug("{} пытается съесть {} в секторе {}", this, plant, currentLocation);
            foodEaten = plant.decreaseBodyWeight(requiredFoodWeight);
        }
        if (foodEaten > 0) {
            increaseSaturation(foodEaten);
            LOGGER.debug(
                    "{} съел {} кг {}. Насыщение: {}. Вес остатков растения: {}",
                    this, foodEaten, plant, getCurrentSaturation(), plant.getBodyWeight()
            );
        } else {
            LOGGER.debug("{} не успел съесть {}", this, plant);
        }
    }
}
