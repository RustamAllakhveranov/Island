package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Runnable {
    private final int MAX_SPEED;
    double weight;
    Sector currentLocation;
    boolean isAlive = true;


    public Animal(int positionX, int positionY, int maxSpeed) {
        Validator.validatePosition(positionX, positionY);
        this.MAX_SPEED = maxSpeed;
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        enterSector(currentLocation);
    }

    public synchronized void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (destination.hasSpace()) {
                changePosition(destination);
            }
        } else {
            System.out.println(this + " не может определиться с направлением");
        }
    }

    //this method shouldn't be synchronized

    private void changePosition(Sector destination) {
        synchronized (this) {
            leaveSector(currentLocation);
            enterSector(destination);
//            System.out.println(this + " changed position on " + positionX + " / " + positionY);
        }
    }

    //this method shouldn't be synchronized
    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(0 - MAX_SPEED, MAX_SPEED + 1);
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName() + " " + getClass().getSimpleName();
    }

    @Override
    public void run() {
        launchLifeCycle();
    }

    protected abstract void launchLifeCycle();

     synchronized void die() {
        isAlive = false;
        System.out.println(this + " dead");
        Thread.currentThread().interrupt();
        leaveSector(currentLocation);
    }

    private void enterSector(Sector sector) {
        sector.addAnimal(this);
        currentLocation = sector;
        System.out.printf("%s перемещается в %s\n", this, sector);
    }

    private void leaveSector(Sector sector) {
        sector.removeAnimal(this);
        currentLocation = null;
    }
}