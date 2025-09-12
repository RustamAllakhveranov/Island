package com.javarush.projects;

import java.util.concurrent.ThreadLocalRandom;

public class Animal {
    private int positionX;
    private int positionY;
    private int maxSpeed;

    public Animal(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        maxSpeed = 3;
    }

    public void navigate() {
        int nextPosX = positionX + getStepsCount();
        int nextPosY = positionY + getStepsCount();
        boolean badX = nextPosX >= Island.getIsland().length || nextPosX < 0;
        boolean badY = nextPosY >= Island.getIsland()[positionX].length || nextPosY < 0;
        if (badX || badY) {
            navigate();
        } else {
            changePosition(nextPosX, nextPosY);
        }
    }

    private void changePosition(int positionX, int positionY) {
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
