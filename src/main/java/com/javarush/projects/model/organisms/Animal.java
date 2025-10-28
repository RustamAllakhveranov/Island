package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.InvalidDestinationException;
import com.javarush.projects.model.island.Island;
import com.javarush.projects.model.island.Sector;
import com.javarush.projects.model.metadata.AnimalSpecies;
import com.javarush.projects.model.utilities.Validator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.DoubleAdder;

public abstract class Animal extends Organism {

    private final AnimalSpecies animalSpecies;
    private final double maxSaturation;
    private final int maxSpeed;
    //DoubleAdder уже синхронизован внутри класса
    private DoubleAdder currentSaturation;

    //в конструкторе не нужно ничего синхронизовать, отрабатывается полностью до получения ссылки на объект
    //модификатор protected не менять!
    protected Animal(int positionX, int positionY) {
        super(positionX, positionY);
        currentSaturation = new DoubleAdder();
        animalSpecies = AnimalSpecies.valueOf(getClass().getSimpleName().toUpperCase());
        maxSaturation = getAnimalSpecies().getMaxSaturation();
        bodyWeight.add(animalSpecies.getWeight());
        this.maxSpeed = animalSpecies.getSpeed();
        enterSector(currentLocation);
    }
//    public Organism choosePrey() {
//
//
//        Set<AnimalSpecies> menu = FoodPreferences.getMenu(this.getClass());
//        Organism prey = currentLocationAnimals.stream().
//                filter(o -> FoodPreferences.isItInMenu(this, o)).
//                findFirst().
//                orElse(null);
//
//        return prey;
//    }

//    public void hunt() {
//        //выясняем кто есть в секторе
//        List<Animal> currentLocationAnimals = currentLocation.getAnimals();
//        //оцениваем каждого на съедобность опираясь на свои предпочтения
//        Organism prey = currentLocationAnimals.stream().
//                filter(o -> FoodPreferences.isItInMenu(this, o)).
//                findFirst().
//                orElse(null);
    //если находим первого съедобного - узнаем каковы шансы его поймать
    //пытаемся поймать
    //если никого подходящего нет - идем в другой сектор и чекаем там
//        int index = ThreadLocalRandom.current().nextInt(0, 1);
//        Organism prey = choosePrey();
//        LOGGER.info("{} выходит охотится на {}!", this, prey);
//        Organism organism = currentLocation.getOrganism(prey);
//        synchronized (this) {
//            if (rabbit != null && rabbit.isAlive) {
//                LOGGER.info("{} убивает {}", this, rabbit);
//                rabbit.die();
//           }
//     }
//    }

    protected synchronized void launchLifeCycle() {
        while (isAlive()) {
            if (isHungry()) {
                tryToSatisfyHunger();
            }
            if (isAlive()) {
                navigate();
            }
        }
    }

    protected Double getCurrentSaturation() {
        return currentSaturation.doubleValue();
    }

    protected synchronized void increaseSaturation(Double saturation) {
        Validator.validateWeight(saturation);
        currentSaturation.add(saturation);
        if (getCurrentSaturation() == getAnimalSpecies().getMaxSaturation()) {
            LOGGER.debug("{} наелся до отвала!", this);
        }
    }

    protected synchronized void decreaseSaturation(Double saturation) {
        Validator.validateWeight(saturation);
        currentSaturation.add(-saturation);
        LOGGER.debug("{} теряет насыщение: {}. Текущий уровень: {}", this, saturation, currentSaturation.doubleValue());
        if (currentSaturation.doubleValue() <= 0) {
            die();
        }
    }

    protected synchronized Double getRequiredFoodWeight() {
        double requiredFoodWeight = maxSaturation - getCurrentSaturation();
        Validator.validateWeight(requiredFoodWeight);
        return requiredFoodWeight;
    }

    protected boolean isHungry() {
        return getCurrentSaturation() < getAnimalSpecies().getMaxSaturation() / 2;
    }

    protected abstract void tryToSatisfyHunger();

    protected abstract Organism findFood();

    private int getStepsCount() {
        return ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
    }

    @Override
    public String toString() {
        return getName();
    }

    private void enterSector(Sector sector) {
        synchronized (this) {
            sector.addAnimal(this);
            currentLocation = sector;
        }
    }

    private void leaveSector(Sector sector) {
        synchronized (this) {
            sector.removeAnimal(this);
            currentLocation = null;
        }
    }

    private void changePosition(Sector destination) {
        if (currentLocation == destination) {
            throw new InvalidDestinationException(String.format("Current location: %s, destination: %s", currentLocation, destination));
        }
        LOGGER.info("{} перемещается из {} в {}", getName(), currentLocation, destination);
        synchronized (this) {
            leaveSector(currentLocation);
            enterSector(destination);
            decreaseSaturation(maxSaturation / 5);
        }
    }

    public void navigate() {
        int nextPosX = currentLocation.getPositionX() + getStepsCount();
        int nextPosY = currentLocation.getPositionY() + getStepsCount();
        if (Validator.isPositionValid(nextPosX, nextPosY)) {
            Sector destination = Island.getInstance().getSector(nextPosX, nextPosY);
            if (currentLocation != destination && destination.hasSpace()) {
                changePosition(destination);
            }
        } else {
            LOGGER.info("{} не может определиться с направлением", getName());
        }
    }

    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }
}