package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Runnable {
    private final int MAX_SPEED;
    double weight;
    Sector currentLocation;
    boolean isAlive = true;


    public Animal(int positionX, int positionY, int maxSpeed) {
        this.MAX_SPEED = maxSpeed;
        currentLocation = Island.getInstance().getSector(positionX, positionY);
        enter(currentLocation);
    }

    public void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            changePosition(nextPosX, nextPosY);
        } else {
            navigate();
        }
    }

    //this method shouldn't be synchronized

    private void changePosition(int positionX, int positionY) {
        synchronized (this) {
            leave(currentLocation);
            Sector nextLocation = Island.getInstance().getSector(positionX, positionY);
            enter(nextLocation);
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

    public synchronized void die() {
        isAlive = false;
        System.out.println(this + " dead");
        Thread.currentThread().interrupt();
        leave(currentLocation);
    }

    public void enter(Sector sector) {
        sector.addAnimal(this);
        currentLocation = sector;
//        System.out.println(String.format("%s left pos: [%s][%s]", this, sector.getPositionX(), sector.getPositionY()));
    }

    public void leave(Sector sector) {
        sector.removeAnimal(this);
        currentLocation = null;
    }
}