package com.javarush.projects.model.metadata;

import com.javarush.projects.model.organisms.*;

import java.util.Map;

public class PredatorPreferences {
    private static final Map<Class<? extends Predator>, Map<Class<? extends Animal>, Integer>> huntChances = Map.of(
            Bear.class, Map.of(
                    Boa.class, 80,
                    Buffalo.class, 20,
                    Deer.class, 80,
                    Duck.class, 10,
                    Goat.class, 70,
                    Horse.class, 40,
                    Mouse.class, 90,
                    Rabbit.class, 80,
                    Sheep.class, 70
            ),
            Boa.class, Map.of(
                    Duck.class, 10,
                    Fox.class, 15,
                    Mouse.class, 40,
                    Rabbit.class, 20
            ),
            Boar.class, Map.of(
                    CaterPillar.class, 90,
                    Mouse.class, 50
            ),
            Duck.class, Map.of(
                    CaterPillar.class, 90
            ),
            Eagle.class, Map.of(
                    Duck.class, 80,
                    Fox.class, 10,
                    Mouse.class, 90,
                    Rabbit.class, 90
            ),
            Fox.class, Map.of(
                    CaterPillar.class, 40,
                    Duck.class, 60,
                    Mouse.class, 90,
                    Rabbit.class, 70
            ),
            Mouse.class, Map.of(
                    CaterPillar.class, 90
            ),
            Wolf.class, Map.of(
                    Boar.class, 15,
                    Buffalo.class, 10,
                    Deer.class, 15,
                    Duck.class, 40,
                    Goat.class, 60,
                    Horse.class, 10,
                    Mouse.class, 80,
                    Rabbit.class, 60,
                    Sheep.class, 70
            )
    );

    private PredatorPreferences() {
    }

//    public static PreyType[] getMenu(OrganismSpecies organismSpecies) {
//        return switch (organismSpecies) {
//            case BEAR -> BearMenu.values();
//            case BOA -> BoaMenu.values();
//            case BOAR -> BoarMenu.values();
//            case DUCK -> DuckMenu.values();
//            case EAGLE -> EagleMenu.values();
//            case FOX -> FoxMenu.values();
//            case MOUSE -> MouseMenu.values();
//            case WOLF -> WolfMenu.values();
//            default -> throw new IllegalStateException("Unexpected value: " + organismSpecies);
//        };
//    }
public static Map<Class<? extends Animal>, Integer> getMenu(Class<? extends Predator> predatorClass) {
    return huntChances.get(predatorClass);
}

//    public static boolean isItInMenu(OrganismSpecies hunterSpecies, OrganismSpecies preySpecies) {
//        return Arrays.asList(getMenu(hunterSpecies)).contains(preySpecies.);
//    }
    public static boolean isItInMenu(Class<? extends Predator> predatorClass, Class<? extends Animal> preyClass) {
        return getMenu(predatorClass).containsKey(preyClass);
    }
    public static Integer getChancesToKill(Class<? extends Predator> predatorClass, Class<? extends Animal> preyClass){
        return getMenu(predatorClass).get(preyClass);
    }
}