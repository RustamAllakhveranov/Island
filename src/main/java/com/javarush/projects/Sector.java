package com.javarush.projects;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    int positionX;
    int positionY;

    public Sector(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    private List animals = new ArrayList<>();

    public void enter(Animal animal) {
        animals.add(animal);
    }

    public void leave(Animal animal) {
        animals.remove(animal);
        System.out.println(animal + " left ");
    }
}
