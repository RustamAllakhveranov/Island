package com.javarush.projects.model.organisms;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    protected volatile Thread individualThread;
    //volatile не требуется
    protected Sector currentLocation;

    public Double getBodyWeight() {
        return bodyWeight.doubleValue();
    }

      public synchronized void decreaseBodyWeight(Double weight) {
        if(getBodyWeight() <= 0){
            return;
        }
        Validator.validateWeight(weight);
        bodyWeight.add(-weight);
        if(getBodyWeight()<= 0){
            die();
        }
    }

    protected Organism(int positionX, int positionY) {
        Validator.validatePosition(positionX, positionY);
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        LOGGER.trace("{} инициализируется в позиции {}", this, currentLocation);
    }

    @Override
    public void run() {
        individualThread = Thread.currentThread();
        launchLifeCycle();
    }

    protected void die() {
        LOGGER.info("{} умирает.", name);
        synchronized (this) {
            individualThread.interrupt();
        }
    }

    protected String getName() {
        return name;
    }

    protected boolean isAlive() {
        return !individualThread.isInterrupted();
    }

    protected abstract void launchLifeCycle();

    @Override
    public String toString() {
        return getName();
    }
}