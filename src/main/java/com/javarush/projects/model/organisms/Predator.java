package com.javarush.projects.model.organisms;

import com.javarush.projects.model.metadata.FoodPreferences;
import com.javarush.projects.model.utilities.Validator;

import java.util.List;

public abstract class Predator extends Animal {
    public Predator(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    protected Organism findFood() {
        LOGGER.info("{} ищет еду!", this);
        List<Animal> animalList = currentLocation.getAnimals();
        synchronized (this) {
            if (!animalList.isEmpty()) {
                for (Animal animal : animalList) {
                    if (FoodPreferences.isItInMenu(this, animal)) {
                        LOGGER.info("{} found animal!", this);
                        return animal;
                    } else {
                        LOGGER.debug("{} is not in menu of {}", animal, this);
                    }
                }
            }
            LOGGER.debug("{} didn't find food at {}", this, currentLocation);
            return null;
        }


    }
    //вроде как синхронизация не нужна, она есть внутри метода die()

    public void eatAnimal(Animal animal) {
        LOGGER.trace("{} trying to eat plant in {}", this, currentLocation);
        double requiredFoodWeight = getRequiredFoodWeight();
        Validator.validateWeight(requiredFoodWeight);
        synchronized (this) {
            double foodWeightToAcquire;
            double preyBodyWeight = animal.getBodyWeight();
            Validator.validateWeight(preyBodyWeight);
            if (preyBodyWeight <= requiredFoodWeight) {
                foodWeightToAcquire = preyBodyWeight;
                Validator.validateWeight(foodWeightToAcquire);
            } else {
                foodWeightToAcquire = requiredFoodWeight;
                Validator.validateWeight(foodWeightToAcquire);
            }
            LOGGER.debug("{} начинает есть {}", this, animal);
            animal.decreaseBodyWeight(foodWeightToAcquire);
            increaseSaturation(foodWeightToAcquire);
            LOGGER.debug(
                    "{} съел {} кг {}. Насыщение: {}. Вес остатков добычи: {}",
                    this, foodWeightToAcquire, animal, getCurrentSaturation(), animal.getBodyWeight()
            );
        }
    }

    @Override
    protected void tryToSatisfyHunger() {
        Animal animal = (Animal) findFood();
        if (animal != null) {
            eatAnimal(animal);
        }
    }
}