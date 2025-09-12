package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    int positionX;
    int positionY;
    private final int MAX_SPEED;
    boolean isAlive = true;

    public Animal(int positionX, int positionY, int maxSpeed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.MAX_SPEED = maxSpeed;
        Island.getSector(positionX, positionY).enter(this);
    }

    public void navigate() {
        int nextPosX = positionX + getStepsCount();
        int nextPosY = positionY + getStepsCount();
        if (isPositionValid(nextPosX, nextPosY)) {
            changePosition(nextPosX, nextPosY);
        } else {
            navigate();
        }
    }

    private boolean isPositionValid(int positionX, int positionY) {
        return positionX >= 0
                && positionX < Island.HEIGHT
                && positionY >= 0
                && positionY < Island.WIDTH;
    }

    private void changePosition(int positionX, int positionY) {
        if (!isPositionValid(positionX, positionY)) {
            throw new RuntimeException("Not Valid Position");
        }
        Sector sector = Island.getSector(this.positionX, this.positionY);
        sector.leave(this);
        this.positionX = positionX;
        this.positionY = positionY;
        Island.getSector(positionX, positionY).enter(this);
//        System.out.println(this + " changed position on " + positionX + " / " + positionY);
    }

    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(0 - MAX_SPEED, MAX_SPEED + 1);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Integer.toHexString(hashCode());
    }
}
