package com.javarush.projects.model.organisms;

import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.utilities.Validator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public abstract class Animal implements Runnable {
    private static AtomicInteger animalsCount = new AtomicInteger(0);
    //volatile не требуется: нет сеттеров
    private String name = getClass().getSimpleName() + "-" + animalsCount.incrementAndGet();
    private final int MAX_SPEED;
    //volatile не требуется: класс DoubleAdder синхронизован
    DoubleAdder weight = new DoubleAdder();
    //volatile не требуется
    Sector currentLocation;
    volatile boolean isAlive = true;

    //в конструкторе не нужно ничего синхронизовать, отрабатывается полностью до получения ссылки на объект
    public Animal(int positionX, int positionY, int maxSpeed) {
        Validator.validatePosition(positionX, positionY);
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        this.MAX_SPEED = maxSpeed;
        enterSector(currentLocation);
        System.out.println(name + " начинает с позиции " + currentLocation);
    }

    //пока что синхронизация не требуется, если не будет изменений:
//getPositionX(), getStepsCount(), getInstance(), getSector(), hasSpace() не меняют данных
//changePosition() синхронизован
    public void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (destination.hasSpace()) {
                changePosition(destination);
            }
        } else {
            System.out.println(name + " не может определиться с направлением");
        }
    }

    //синхронизация в порядке: покинуть место = появится в другом, промежуточных состояний не бывает
    //нельзя находится "нигде"
    private synchronized void changePosition(Sector destination) {
        System.out.printf("%s перемещается из %s в %s\n", name, currentLocation, destination);
        leaveSector(currentLocation);
        enterSector(destination);
    }

    //синхронизация не требуется: класс ThreadLocalRandom синхронизован
    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(0 - MAX_SPEED, MAX_SPEED + 1);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        launchLifeCycle();
    }

    protected abstract void launchLifeCycle();

    //синхронизован правильно
    void die() {
        synchronized (this) {
            if (isAlive) {
                isAlive = false;
                System.out.println(name + " dead");
                Thread.currentThread().interrupt();
            }
        }
    }

    //метод нужен в отдельном виде, поскольку будет использоваться во время размещения при создании
    private synchronized void enterSector(Sector sector) {
        sector.addAnimal(this);
        currentLocation = sector;
    }

    //метод нужен в отдельном виде, поскольку он будет использоваться после смерти животного
    synchronized void leaveSector(Sector sector) {
        sector.removeAnimal(this);
        currentLocation = null;
    }

}