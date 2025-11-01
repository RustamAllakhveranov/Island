package com.javarush.projects.model.island;

import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class Sector {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sector.class);
    private final int positionX;
    private final int positionY;
    private final Map<Class<? extends Animal>, Set<Animal>> animalsMap = new ConcurrentHashMap<>();
    private final Set<Plant> plants = new CopyOnWriteArraySet<>();

    public Sector(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //computeIfAbsent уже синхронизован!
    public Set<Animal> getOrCreateAnimalSet(Class<? extends Animal> animalClass) {
        return animalsMap.computeIfAbsent(
                animalClass,
                v -> new CopyOnWriteArraySet<>());
    }

    //добавить проверку на количество особей и возвращать либо количество мест, либо просто true если есть места.
    public synchronized boolean addAnimal(Animal animal) {
        Class<? extends Animal> key = animal.getClass();
        Set<Animal> animalSet = getOrCreateAnimalSet(key);
        return hasSpaceForAnimal(key) && animalSet.add(animal);
    }

    public synchronized boolean removeAnimal(Animal animal) {
        Class<? extends Animal> key = animal.getClass();
        Set<Animal> animalSet = animalsMap.get(key);
        boolean removed = animalSet.remove(animal);
        if (animalSet.isEmpty()) {
            animalsMap.remove(key);
        }
        return removed;
    }

    public int getPositionX() {
        return positionX;
    }

    //синхронизация не требуется: геттер
    public int getPositionY() {
        return positionY;
    }

    @Override
    public String toString() {
        return String.format("[%d][%d]", positionX, positionY);
    }

    public boolean hasSpaceForAnimal(Class<? extends Animal> animalClass) {
        int maxSpeciesOnSector = OrganismSpecies.getSpeciesByClass(animalClass).getMaxSpeciesOnSector();
        return maxSpeciesOnSector > getOrCreateAnimalSet(animalClass).size();
    }

    public Set<Class<? extends Animal>> getResidentsTypes() {
        return animalsMap.keySet();
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public synchronized boolean hasSpaceForPlant() {
        return plants.size() <= OrganismSpecies.PLANT.getMaxSpeciesOnSector();
    }

    public synchronized boolean addPlant(Plant plant) {
        return hasSpaceForPlant() && plants.add(plant);
    }

    public boolean removePlant(Plant plant) {
        return plants.remove(plant);
    }
}