package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public class Animal {
    private int positionX;
    private int positionY;
    private static final int MAX_SPEED = 3;

    public Animal(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
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
        System.out.println(this + " changed position on " + positionX + " / " + positionY);
    }

    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(0 - MAX_SPEED, MAX_SPEED + 1);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Integer.toHexString(hashCode());
    }
}
