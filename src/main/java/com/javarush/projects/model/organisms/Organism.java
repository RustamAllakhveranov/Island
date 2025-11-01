package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public abstract class Organism implements Runnable {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Organism.class);
    private static final AtomicInteger ORGANISMS_COUNT = new AtomicInteger(0);
    //volatile не требуется: нет сеттеров
    //name перенести в Organism, присвоение оставить в наследниках
    private final String name = getClass().getSimpleName() + "#" + ORGANISMS_COUNT.incrementAndGet();
    //volatile не требуется: класс DoubleAdder синхронизован
    protected DoubleAdder bodyWeight = new DoubleAdder();
    private final OrganismSpecies organismSpecies;
    protected volatile Thread individualThread;
    //volatile не требуется
    protected Sector currentLocation;

    protected Organism(int positionX, int positionY) {
        Validator.validatePosition(positionX, positionY);
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        LOGGER.trace("{} инициализируется в позиции {}", this, currentLocation);
        organismSpecies = OrganismSpecies.valueOf(getClass().getSimpleName().toUpperCase());
        bodyWeight.add(organismSpecies.getWeight());
    }

    @Override
    public void run() {
        individualThread = Thread.currentThread();
        launchLifeCycle();
    }

    protected synchronized double getBodyWeight() {
        double weight = bodyWeight.doubleValue();
        Validator.validateWeight(weight);
        return weight;
    }

    protected synchronized double decreaseBodyWeight(double weight) {
        if (getBodyWeight() == 0){
            LOGGER.debug("{} уже съели!", this);
            return 0.0;
        }
        Validator.validateWeight(weight);
        double decreasedWeight = Math.min(weight, getBodyWeight());
        bodyWeight.add(-decreasedWeight);
        if (getBodyWeight() == 0) eraseBody();
        return decreasedWeight;
    }

    protected void die() {
        LOGGER.info("{} умирает.", this);
        synchronized (this) {
            individualThread.interrupt();
        }
    }

    protected boolean isAlive() {
        return !individualThread.isInterrupted();
    }

    protected boolean isLucky(int chancesOfLuck) {
        return ThreadLocalRandom.current().nextInt(100) <= chancesOfLuck;
    }

    public OrganismSpecies getOrganismSpecies() {
        return organismSpecies;
    }

    protected abstract void eraseBody();

    @Override
    public String toString() {
        return name;
    }

    protected boolean isDead() {
        return individualThread.isInterrupted();
    }

    protected abstract void launchLifeCycle();

}