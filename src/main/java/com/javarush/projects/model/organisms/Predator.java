package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.CannibalismException;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.metadata.PredatorPreferences;
import com.javarush.projects.model.utilities.Validator;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {
    protected Predator(Sector startPosition) {
        super(startPosition);
    }

    //
    protected synchronized void tryToSatisfyHunger() {
        hunt();
    }

//    private void hunt() {
//        synchronized (this) {
//            Class<? extends Animal> preyClass = getNextPreyClass();
//            LOGGER.debug("{} почуял {} в секторе {}", this, preyClass.getSimpleName(), currentLocation);
//            if (PredatorPreferences.isItInMenu(getClass(), preyClass)) {
//                if(preyClass == getClass()){
//                    String message = String.format("Cannibalism detected: %s", this);
//                    throw new CannibalismException(message);
//                }
//                Animal prey = findPrey(preyClass);
//                if (prey == null) {
//                    String message = String.format("Prey of %s is NULL", this);
//                    LOGGER.error(message);
//                    throw new NullPointerException(message);
//                }
//                LOGGER.debug("{} нашел еду в секторе {}: {}", this, currentLocation, prey);
//                if (prey.isAlive()) {
//                    int chancesToKill = PredatorPreferences.getChancesToKill(getClass(), preyClass);
//                    LOGGER.debug("{} пытается убить {} в секторе {}", this, prey, currentLocation);
//                    tryToKill(chancesToKill, prey);
//                }
//                if (prey.isDead()) {
//                    tryToEatAnimal(prey);
//                }
//            }
//        }
//    }


//    private Class<? extends Animal> getNextPreyClass() {
//        Set<Class<? extends Animal>> residentsTypes = currentLocation.getResidentsTypes();
//        int preyClassIndex = ThreadLocalRandom.current().nextInt(residentsTypes.size());
//        return residentsTypes.stream()
//                .toList()
//                .get(preyClassIndex);
//    }

//    protected Animal findPrey(Class<? extends Animal> preyClass) {
//        LOGGER.debug("{} ищет {} в секторе{}", this, preyClass.getSimpleName(), currentLocation);
//        synchronized (this) {
//            return currentLocation.getOrCreateAnimalSet(preyClass)
//                    .stream()
//                    .findFirst()
//                    .orElse(null);
//        }
//    }

    public synchronized void tryToKill(int chancesToKill, Animal animal) {
        if (isLucky(chancesToKill)) {
            LOGGER.debug("{} убивает {}", this, animal);
            killAnimal(animal);
        }
    }

    private void killAnimal(Animal animal) {
        animal.die();
    }
    public synchronized void hunt() {
        Class<? extends Animal> preyClass = currentLocation.getRandomResidentClass();
        LOGGER.debug("{} почуял {} в секторе {}", this, preyClass.getSimpleName(), currentLocation);
        if (PredatorPreferences.isItInMenu(getClass(), preyClass)) {
            Validator.validateCannibalism(getClass(), preyClass);
            LOGGER.debug("{} ищет {} в секторе{}", getClass().getSimpleName(), preyClass.getSimpleName(), currentLocation);
            Animal prey = currentLocation.getRandomAnimalByClass(preyClass);
            if (prey == null) {
                String message = String.format(
                        "Prey of %s is NULL in %s. Target class: %s", this, currentLocation, preyClass.getSimpleName()
                );
                LOGGER.error(message);
                throw new NullPointerException(message);
            }
            LOGGER.debug("{} нашел еду в секторе {}: {}", this, currentLocation, prey);
            if (prey.isAlive()) {
                int chancesToKill = PredatorPreferences.getChancesToKill(getClass(), preyClass);
                LOGGER.debug("{} пытается убить {} в секторе {}", this, prey, currentLocation);
                tryToKill(chancesToKill, prey);
            }
            if (prey.isDead()) {
                tryToEatAnimal(prey);
            }
        }
    }
    public void tryToEatAnimal(Animal animal) {
        LOGGER.debug("{} пытается съесть {} в секторе {}", this, animal, currentLocation);
        synchronized (this) {
            double foodEaten = animal.decreaseBodyWeight(getRequiredFoodWeight());
            if (foodEaten > 0) {
                increaseSaturation(foodEaten);
                LOGGER.debug(
                        "{} съел {} кг {}. Насыщение: {}. Вес остатков добычи: {}",
                        this, foodEaten, animal, getCurrentSaturation(), animal.getBodyWeight()
                );
            } else {
                LOGGER.debug("{} не успел съесть {}", this, animal);
            }
        }
    }
}