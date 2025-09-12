package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public class Animal {
    private int positionX;
    private int positionY;
    private final int maxSpeed;

    public Animal(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.maxSpeed = 3;
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
        if (isPositionValid(positionX, positionY)) {
            throw new RuntimeException("Not Valid Position");
        }
        Animal[][] island = Island.getIsland();
        island[this.positionX][this.positionY] = null;
        this.positionX = positionX;
        this.positionY = positionY;
        island[positionX][positionY] = this;
        System.out.println(this + " changed position on " + positionX + " / " + positionY);
    }

    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(0 - maxSpeed, maxSpeed + 1);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Integer.toHexString(hashCode());
    }
}
