package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;
import com.javarush.projects.model.exceptions.InvalidDestinationException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.utilities.Validator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.DoubleAdder;

import static com.javarush.projects.Main.GLOBAL_ACTION_COUNTER;

public abstract class Animal extends Organism {
    private final int maxSpeed;
    private final double saturationLimit;
    private final DoubleAdder currentSaturation;

    protected Animal(Sector startPosition) {
        super(startPosition);
        this.maxSpeed = getOrganismSpecies().getSpeed();
        saturationLimit = getOrganismSpecies().getSaturationLimit();
        currentSaturation = new DoubleAdder();
        currentSaturation.add(saturationLimit);
    }

    @Override
    protected synchronized void eraseBody() {
        boolean removed = currentLocation.removeAnimal(this);
        if (removed) {
            LOGGER.info("{} erased from {}", this, currentLocation);
        } else {
            String message = String.format("%s wasn't erased from %s", this, currentLocation);
            LOGGER.error(message);
            throw new ErasingException(message);
        }
    }

    protected synchronized void launchLifeCycle() {
        enterSector(currentLocation);
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
        if (getCurrentSaturation() == getOrganismSpecies().getSaturationLimit()) {
            LOGGER.info("{} наелся до отвала!", this);
        }
    }

    protected synchronized void decreaseSaturation(double saturation) {
        Validator.validateWeight(saturation);
        double decreasedValue = Math.min(saturation, getCurrentSaturation());
        currentSaturation.add(-decreasedValue);
        LOGGER.info("{} теряет насыщение: {}. Текущий уровень: {}", this, decreasedValue, getCurrentSaturation());
        Validator.validateWeight(getCurrentSaturation());
        if (getCurrentSaturation() == 0) {
            die();
        }
    }

    protected double getRequiredFoodWeight() {
        double requiredFoodWeight = saturationLimit - getCurrentSaturation();
        Validator.validateWeight(requiredFoodWeight);
        return requiredFoodWeight;
    }

    protected boolean isHungry() {
        return getCurrentSaturation() < getOrganismSpecies().getSaturationLimit() / 2;
    }

    protected abstract void tryToSatisfyHunger();


    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
    }

    private void enterSector(Sector sector) {
        sector.addAnimal(this);
        currentLocation = sector;
    }

    private void leaveSector(Sector sector) {
        sector.removeAnimal(this);
        currentLocation = null;
    }

    private void changePosition(Sector destination) {
        if (currentLocation == destination) {
            String message = String.format("Current location: %s, destination: %s", currentLocation, destination);
            LOGGER.error(message);
            throw new InvalidDestinationException(message);
        }
        Sector firstLock = currentLocation.hashCode() > destination.hashCode() ? currentLocation : destination;
        Sector secondLock = firstLock == currentLocation ? destination : currentLocation;
        synchronized (firstLock) {
            synchronized (secondLock) {
                LOGGER.debug("ACTION_ID:{} | {} собирается переместится из {} в {}", GLOBAL_ACTION_COUNTER.incrementAndGet(), this, currentLocation, destination);
                leaveSector(currentLocation);
                enterSector(destination);
                decreaseSaturation(saturationLimit / 5);
                LOGGER.info("ACTION_ID:{} | {} перемеСТИЛСЯ в {}", GLOBAL_ACTION_COUNTER.incrementAndGet(), this, destination);
            }
        }
    }

    public synchronized void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (currentLocation != destination && destination.hasSpaceForAnimal(this.getClass())) {
                changePosition(destination);
            }
        } else {
            LOGGER.debug("{} не может определиться с направлением", this);
        }
    }
}