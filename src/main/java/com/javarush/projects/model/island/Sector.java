package com.javarush.projects.model.island;

import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Plant;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;


public class Sector {
    private final int positionX;
    private final int positionY;
    private final Map<Class<? extends Animal>, Set<Animal>> animalsMap = new ConcurrentHashMap<>();
    private final Set<Plant> plants = new CopyOnWriteArraySet<>();

    public Sector(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Set<Animal> getOrCreateAnimalSet(Class<? extends Animal> animalClass) {
        return animalsMap.computeIfAbsent(
                animalClass,
                v -> new CopyOnWriteArraySet<>());
    }

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

    public synchronized boolean hasSpaceForAnimal(Class<? extends Animal> animalClass) {
        int maxSpeciesOnSector = OrganismSpecies.getSpeciesByClass(animalClass).getSectorSpeciesLimit();
        return maxSpeciesOnSector >= getOrCreateAnimalSet(animalClass).size();
    }

    private Set<Class<? extends Animal>> getResidentClassSet() {
        return animalsMap.keySet();
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public boolean hasSpaceForPlant() {
        return plants.size() <= OrganismSpecies.PLANT.getSectorSpeciesLimit();
    }

    public synchronized boolean addPlant(Plant plant) {
        return hasSpaceForPlant() && plants.add(plant);
    }

    public synchronized boolean removePlant(Plant plant) {
        return plants.remove(plant);
    }

    public synchronized Animal getRandomAnimalByClass(Class<? extends Animal> preyClass) {
        return getOrCreateAnimalSet(preyClass)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public synchronized Class<? extends Animal> getRandomResidentClass() {
        List<Class<? extends Animal>> preyClassList = getResidentClassSet()
                .stream()
                .filter(key -> !animalsMap.get(key).isEmpty())
                .collect(Collectors.toList());
        if (preyClassList.isEmpty()) return null;
        Collections.shuffle(preyClassList);
        return preyClassList.getFirst();
    }
}