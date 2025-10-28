package com.javarush.projects.model.island;

import com.javarush.projects.model.metadata.AnimalSpecies;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Organism;
import com.javarush.projects.model.organisms.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Sector {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sector.class);
    private final int positionX;
    private final int positionY;
    private static final int MAX_RESIDENTS_NUMBER = 3;
    private final List<Animal> animals = Collections.synchronizedList(new ArrayList<>());
    private final List<Plant> plants = Collections.synchronizedList(new ArrayList<>());

    public Sector(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Итерировать synchronizedList по-прежнему нужно синхронизованно!
    //метод под вопросом
    public synchronized Organism getOrganism(AnimalSpecies species) {
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equals(species.getNormalName())) {
                return animal;
            }
        }
        return null;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    //синхронизация не требуется: список синхронизован
    public boolean hasSpace() {
        int residentsCount = animals.size();
        return residentsCount < MAX_RESIDENTS_NUMBER;
    }

    //синхронизация не требуется: геттер
    public int getPositionX() {
        return positionX;
    }

    //синхронизация не требуется: геттер
    public int getPositionY() {
        return positionY;
    }

    //синхронизация не требуется: список синхронизован
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    //синхронизация не требуется: список синхронизован
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    @Override
    public String toString() {
        return String.format("[%d][%d]", positionX, positionY);
    }
}