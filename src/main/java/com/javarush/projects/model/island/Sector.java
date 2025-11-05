package com.javarush.projects.model.island;

import com.javarush.projects.model.metadata.OrganismSpecies;
import com.javarush.projects.model.metadata.PredatorPreferences;
import com.javarush.projects.model.organisms.Animal;
import com.javarush.projects.model.organisms.Plant;
import com.javarush.projects.model.organisms.Predator;
import com.javarush.projects.model.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;


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
    public synchronized Set<Animal> getOrCreateAnimalSet(Class<? extends Animal> animalClass) {
        return animalsMap.computeIfAbsent(
                animalClass,
                v -> new CopyOnWriteArraySet<>());
    }

    //добавить проверку на количество особей и возвращать либо количество мест, либо просто true если есть места.
    public synchronized boolean addAnimal(Animal animal) {
        LOGGER.debug("{} добавляется в карту {}", animal, this);
        Class<? extends Animal> key = animal.getClass();
        Set<Animal> animalSet = getOrCreateAnimalSet(key);
//        return hasSpaceForAnimal(key) && animalSet.add(animal);
        boolean added = hasSpaceForAnimal(key) && animalSet.add(animal);
        LOGGER.debug("{} добавлен в карту {} - {}", animal, this, added);
        return added;
    }

    public synchronized boolean removeAnimal(Animal animal) {
        LOGGER.debug("{} удаляется из карты {}", animal, this);
        Class<? extends Animal> key = animal.getClass();
        Set<Animal> animalSet = animalsMap.get(key);
        boolean removed = animalSet.remove(animal);
        LOGGER.debug("{} добавлен в карту {} - {}", animal, this, removed);
        if (animalSet.isEmpty()) {
            animalsMap.remove(key);
            LOGGER.debug("класс {} удален из сектора {}", key.getSimpleName(), this);
            LOGGER.debug("Проверка удаления класса {} из сектора {}: {}", key.getSimpleName(), this, animalsMap.get(key));
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

    public synchronized Set<Class<? extends Animal>> getResidentClassSet() {
        return animalsMap.keySet();
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public synchronized boolean hasSpaceForPlant() {
        return plants.size() <= OrganismSpecies.PLANT.getSectorSpeciesLimit();
    }

    public synchronized boolean addPlant(Plant plant) {
        return hasSpaceForPlant() && plants.add(plant);
    }

    public synchronized boolean removePlant(Plant plant) {
        return plants.remove(plant);
    }



    //getRandomAnimalByClass обязан быть в классе Сектор!
    public synchronized Animal getRandomAnimalByClass(Class<? extends Animal> preyClass) {
        return getOrCreateAnimalSet(preyClass)
                .stream()
                .findFirst()
                .orElse(null);
    }

    //getRandomResidentClass обязан быть в классе Сектор!
    public synchronized Class<? extends Animal> getRandomResidentClass() {
        Class<? extends Animal> residentClass;
        Set<Class<? extends Animal>> residentClassSet = getResidentClassSet();
        int residentClassIndex = ThreadLocalRandom.current().nextInt(residentClassSet.size());
        residentClass = residentClassSet.stream()
                .toList()
                .get(residentClassIndex);
        LOGGER.debug("Class {} has {} individuals in {}",
                residentClass.getSimpleName(), animalsMap.get(residentClass).size(), this);
        return residentClass;
    }
}