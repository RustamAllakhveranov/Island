package com.javarush.projects.model.organisms;

import com.javarush.projects.model.island.Sector;

public class Mouse extends Predator implements CanEatPlant {
    public Mouse(Sector startPosition) {
        super(startPosition);
    }

    @Override
    public void tryToEatPlant(Plant plant) {
        double requiredFoodWeight = super.getRequiredFoodWeight();
        double foodWeight = plant.getBodyWeight();
        if (foodWeight <= requiredFoodWeight) {
            increaseSaturation(foodWeight);
            plant.decreaseBodyWeight(foodWeight);
        } else {
            plant.decreaseBodyWeight(requiredFoodWeight - getCurrentSaturation());
        }
    }

    @Override
    public void graze() {
        throw new UnsupportedOperationException();
    }
}
