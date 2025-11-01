package com.javarush.projects.model.organisms;

public class Duck extends Predator implements CanEatPlant {
    public Duck(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tryToEatPlant(Plant plant) {
        double requiredFoodWeight = getRequiredFoodWeight();
        double foodWeight = plant.getBodyWeight();
        if (foodWeight <= requiredFoodWeight) {
            increaseSaturation(foodWeight);
            plant.decreaseBodyWeight(foodWeight);
        } else {
            plant.decreaseBodyWeight(requiredFoodWeight - getCurrentSaturation());
        }
    }
}
