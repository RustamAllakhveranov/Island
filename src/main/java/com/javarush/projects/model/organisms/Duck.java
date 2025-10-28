package com.javarush.projects.model.organisms;

import com.javarush.projects.model.organisms.CanEatPlant;
import com.javarush.projects.model.organisms.Plant;
import com.javarush.projects.model.organisms.Predator;

public class Duck extends Predator implements CanEatPlant {
    public Duck(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tryToEatPlant(Plant plant) {
        Double requiredFoodWeight = super.getRequiredFoodWeight();
        Double foodWeight = plant.getBodyWeight();
        if (foodWeight <= requiredFoodWeight) {
            increaseSaturation(foodWeight);
            plant.decreaseBodyWeight(foodWeight);
        } else {
            plant.decreaseBodyWeight(requiredFoodWeight - getCurrentSaturation());
        }
    }
}
