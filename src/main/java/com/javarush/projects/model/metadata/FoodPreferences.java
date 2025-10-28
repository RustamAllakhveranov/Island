package com.javarush.projects.model.metadata;

import com.javarush.projects.model.metadata.predator_menu.*;
import com.javarush.projects.model.organisms.Animal;

import java.util.Arrays;

public class FoodPreferences {

    private FoodPreferences() {
    }

    public static Menu[] getMenu(AnimalSpecies animalSpecies) {
        return switch (animalSpecies) {
            case BEAR -> BearMenu.values();
            case BOA -> BoaMenu.values();
            case BOAR -> BoarMenu.values();
            case DUCK -> DuckMenu.values();
            case EAGLE -> EagleMenu.values();
            case FOX -> FoxMenu.values();
            case MOUSE -> MouseMenu.values();
            case WOLF -> WolfMenu.values();
            default -> throw new IllegalStateException("Unexpected value: " + animalSpecies);
        };
    }

    public static boolean isItInMenu(Animal hunter, Animal prey) {
        return Arrays.stream(getMenu(hunter.getAnimalSpecies())).
                anyMatch(o -> o.equals(prey.getAnimalSpecies()));
    }
}