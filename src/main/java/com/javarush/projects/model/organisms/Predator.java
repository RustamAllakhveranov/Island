package com.javarush.projects.model.organisms;

import com.javarush.projects.model.metadata.PredatorPreferences;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {
    protected Predator(int positionX, int positionY) {
        super(positionX, positionY);
    }

    //
    protected synchronized void tryToSatisfyHunger() {
        hunt();
    }

    private void hunt() {
        synchronized (this) {
            Class<? extends Animal> preyClass = getNextPreyClass();
            if (PredatorPreferences.isItInMenu(getClass(), preyClass)) {
                Animal animal = findPrey(preyClass);
                if(animal == null){
                    throw new NullPointerException(String.format("Prey of %s is: %s", this, animal));
                }
                LOGGER.debug("{} нашел еду: {}", this, animal);
                if (animal.isAlive()) {
                    int chancesToKill = PredatorPreferences.getChancesToKill(getClass(), preyClass);
                    LOGGER.debug("{} пытается убить {}", this, animal);
                    tryToKill(chancesToKill, animal);
                }
                if (isDead()) {
                    tryToEatAnimal(animal);
                }
            }
        }
    }


    private Class<? extends Animal> getNextPreyClass() {
        Set<Class<? extends Animal>> residentsTypes = currentLocation.getResidentsTypes();
        int preyClassIndex = ThreadLocalRandom.current().nextInt(residentsTypes.size());
        return residentsTypes.stream()
                .toList()
                .get(preyClassIndex);
    }

    //tryToKill: не синхронизовывать

    //синхронизован
    //killAnimal: не синхронизовывать
    protected Animal findPrey(Class<? extends Animal> preyClass) {
        synchronized (this) {
            LOGGER.debug("{} ищет еду в секторе{}", this, currentLocation);
            return currentLocation.getOrCreateAnimalSet(preyClass)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }
    }

    private void tryToKill(int chancesToKill, Animal animal) {
        if (isLucky(chancesToKill)) {
            killAnimal(animal);
        }
    }

    private void killAnimal(Animal animal) {
        animal.die();
    }

    private void tryToEatAnimal(Animal animal) {
        LOGGER.debug("{} trying to eat {} in {}", this, animal, currentLocation);
        synchronized (this) {
            double foodEaten = animal.decreaseBodyWeight(getRequiredFoodWeight());
            if (foodEaten > 0) {
                increaseSaturation(foodEaten);
                LOGGER.debug(
                        "{} съел {} кг {}. Насыщение: {}. Вес остатков еды: {}",
                        this, foodEaten, animal, getCurrentSaturation(), animal.getBodyWeight()
                );
            } else {
                LOGGER.debug("{} не успел съесть {}", this, animal);
            }
        }
    }


}