package com.javarush.projects.model.organisms;

public class Mouse extends Predator implements CanEatPlant {
    public Mouse(int positionX, int positionY) {
        super(positionX, positionY);
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
