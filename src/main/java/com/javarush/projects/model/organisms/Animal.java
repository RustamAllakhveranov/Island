package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;
import com.javarush.projects.model.exceptions.InvalidDestinationException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.utilities.Validator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.DoubleAdder;

public abstract class Animal extends Organism {
    private final double maxSaturation;
    private final int maxSpeed;
    private final DoubleAdder currentSaturation;

    protected Animal(int positionX, int positionY) {
        super(positionX, positionY);
        maxSaturation = getOrganismSpecies().getMaxSaturation();
        currentSaturation = new DoubleAdder();
        currentSaturation.add(maxSaturation);
        this.maxSpeed = getOrganismSpecies().getSpeed();
        enterSector(currentLocation);
    }

    @Override
    protected void eraseBody() {
        boolean removed = currentLocation.removeAnimal(this);
        if (removed) {
            LOGGER.debug("{} erased from {}", this, currentLocation);
        } else {
            throw new ErasingException(String.format("%s wasn't erased from %s", this, currentLocation));
        }
    }

    protected synchronized void launchLifeCycle() {
        while (isAlive()) {
            if (isHungry()) {
                tryToSatisfyHunger();
            }
            if (isAlive()) {
                navigate();
            }
        }
    }

    protected double getCurrentSaturation() {
        return currentSaturation.doubleValue();
    }

    protected synchronized void increaseSaturation(double saturation) {
        Validator.validateWeight(saturation);
        currentSaturation.add(saturation);
        if (getCurrentSaturation() == getOrganismSpecies().getMaxSaturation()) {
            LOGGER.debug("{} наелся до отвала!", this);
        }
    }

    protected synchronized void decreaseSaturation(double saturation) {
        Validator.validateWeight(saturation);
        double decreasedValue = Math.min(saturation, getCurrentSaturation());
        currentSaturation.add(-decreasedValue);
        LOGGER.debug("{} теряет насыщение: {}. Текущий уровень: {}", this, decreasedValue, getCurrentSaturation());
        Validator.validateWeight(getCurrentSaturation());
        if (getCurrentSaturation() == 0) {
            die();
        }
    }

    protected synchronized double getRequiredFoodWeight() {
        double requiredFoodWeight = maxSaturation - getCurrentSaturation();
        Validator.validateWeight(requiredFoodWeight);
        return requiredFoodWeight;
    }

    protected boolean isHungry() {
        return getCurrentSaturation() < getOrganismSpecies().getMaxSaturation() / 2;
    }

    protected abstract void tryToSatisfyHunger();


    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
    }

    private void enterSector(Sector sector) {
        synchronized (this) {
            sector.addAnimal(this);
            currentLocation = sector;
        }
    }

    private void leaveSector(Sector sector) {
        synchronized (this) {
            sector.removeAnimal(this);
            currentLocation = null;
        }
    }

    private void changePosition(Sector destination) {
        if (currentLocation == destination) {
            throw new InvalidDestinationException(String.format("Current location: %s, destination: %s", currentLocation, destination));
        }
        LOGGER.info("{} перемещается из {} в {}", this, currentLocation, destination);
        synchronized (this) {
            leaveSector(currentLocation);
            enterSector(destination);
            decreaseSaturation(maxSaturation / 5);
        }
    }

    public void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (currentLocation != destination && destination.hasSpaceForAnimal(this.getClass())) {
                changePosition(destination);
            }
        } else {
            LOGGER.info("{} не может определиться с направлением", this);
        }
    }
}