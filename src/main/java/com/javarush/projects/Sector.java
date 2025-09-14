package com.javarush.projects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sector {
    private final int positionX;
    private final int positionY;
    private final static int maxResidentsNumber = 3;
    private List<Animal> animals = Collections.synchronizedList(new ArrayList<>());

    public Sector(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Итерировать synchronizedList по-прежнему нужно синхронизованно!

    public synchronized Rabbit getRabbit() {
        for (Animal animal : animals) {
            if (animal instanceof Rabbit) {
                return (Rabbit) animal;
            }
        }
        return null;
    }

    public boolean hasSpace(){
        return animals.size() < maxResidentsNumber;
    }
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
}