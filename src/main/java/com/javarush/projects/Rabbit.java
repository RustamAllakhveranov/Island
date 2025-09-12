package com.javarush.projects;

public class Rabbit extends Animal {
    public Rabbit(int positionX, int positionY) {
        super(positionX, positionY, 2);
    }

    public void die() {
        isAlive = false;
        Island.getSector(positionX, positionY).leave(this);
    }
}
