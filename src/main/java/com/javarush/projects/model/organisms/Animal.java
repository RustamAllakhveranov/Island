package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.SameDestinationException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public abstract class Animal implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Animal.class);
    private volatile Thread animalThread;
    private static final AtomicInteger ANIMALS_COUNT = new AtomicInteger(0);
    //volatile не требуется: нет сеттеров
    private final String name = getClass().getSimpleName() + "-" + ANIMALS_COUNT.incrementAndGet();
    private final int maxSpeed;
    //volatile не требуется: класс DoubleAdder синхронизован
    DoubleAdder weight = new DoubleAdder();
    //volatile не требуется
    Sector currentLocation;
    volatile boolean isAlive = true;

    //в конструкторе не нужно ничего синхронизовать, отрабатывается полностью до получения ссылки на объект
    //модификатор protected не менять!
    protected Animal(int positionX, int positionY, int maxSpeed) {
        Validator.validatePosition(positionX, positionY);
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        this.maxSpeed = maxSpeed;
        enterSector(currentLocation);
    }

    //пока что синхронизация не требуется, если не будет изменений:
    //getPositionX(), getStepsCount(), getInstance(), getSector(), hasSpace() не меняют данных
    //changePosition() синхронизован
    public void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (currentLocation != destination && destination.hasSpace()) {
                changePosition(destination);
            }
        } else {
            LOGGER.info("{} не может определиться с направлением", name);
        }
    }

    //синхронизация в порядке: покинуть место = появится в другом, промежуточных состояний не бывает
    //нельзя находится "нигде"
    //залогировано
    private void changePosition(Sector destination) {
        if(currentLocation == destination){
            throw new SameDestinationException(String.format("Current location: %s, destination: %s", currentLocation, destination));
        }
        LOGGER.info("{} перемещается из {} в {}", name, currentLocation, destination);
        synchronized (this) {
            leaveSector(currentLocation);
            enterSector(destination);
        }
    }

    //синхронизация не требуется: класс ThreadLocalRandom синхронизован
    //логирование не требуется
    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
    }
    //логирование не требуется
    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        animalThread = Thread.currentThread();
        launchLifeCycle();
    }

    protected abstract void launchLifeCycle();

    //синхронизован правильно
    //залогировано
    void die() {
        synchronized (this) {
            isAlive = false;
            animalThread.interrupt();
        }
        LOGGER.info("{} умирает.", name);
    }

    //метод нужен в отдельном виде, поскольку будет использоваться во время размещения при создании
    //залогировано
    private synchronized void enterSector(Sector sector) {
        LOGGER.debug("{} перемещается в сектор{}", this, sector);
        synchronized (this) {
            sector.addAnimal(this);
            currentLocation = sector;
        }
    }

    //метод нужен в отдельном виде, поскольку он будет использоваться после смерти животного
    //залогировано
    void leaveSector(Sector sector) {
        LOGGER.debug("{} покидает сектор{}", this, sector);
        synchronized (this) {
            sector.removeAnimal(this);
            currentLocation = null;
        }
    }
}