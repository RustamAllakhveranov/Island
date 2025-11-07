package com.javarush.projects.model.organisms;

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
    private final String name = getClass().getSimpleName() + "#" + ORGANISMS_COUNT.incrementAndGet();
    protected DoubleAdder bodyWeight = new DoubleAdder();
    private final OrganismSpecies organismSpecies;
    protected volatile Thread individualThread;
    //volatile не требуется
    protected volatile Sector currentLocation;

    protected Organism(Sector startPosition) {
        Validator.validatePosition(startPosition);
        currentLocation = startPosition;
        LOGGER.info("{} инициализируется в позиции {}", this, currentLocation);
        organismSpecies = OrganismSpecies.getSpeciesByClass(this.getClass());
        bodyWeight.add(organismSpecies.getWeight());
        LOGGER.trace("Начальный вес {}: {}", this, getBodyWeight());
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

    protected double decreaseBodyWeight(double weight) {
        if (getBodyWeight() == 0) {
            LOGGER.debug("{} уже съели!", this);
            return 0.0;
        }
        Validator.validateWeight(weight);
        synchronized (this) {
            double decreasedWeight = Math.min(weight, getBodyWeight());
            LOGGER.debug("{} весом {} кг теряет {} кг массы", this, getBodyWeight(), decreasedWeight);
            bodyWeight.add(-decreasedWeight);
            if (getBodyWeight() == 0) eraseBody();
            return decreasedWeight;
        }
    }

    public synchronized void die() {
        LOGGER.info("{} умирает в секторе {}", this, currentLocation);
        if (individualThread != null && isAlive()) {
            individualThread.interrupt();
        }
    }

    public synchronized boolean isAlive() {
        return !individualThread.isInterrupted();
    }

    public boolean isLucky(int chancesOfLuck) {
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

    public synchronized boolean isDead() {
        return individualThread.isInterrupted();
    }

    protected abstract void launchLifeCycle();

}