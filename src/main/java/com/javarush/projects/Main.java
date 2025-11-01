package com.javarush.projects;

import com.javarush.projects.controller.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.debug("Main started!");
        GameManager gameManager = new GameManager();
        gameManager.startSimulation(1);
//        Menu[] menu = FoodPreferences.getMenu(OrganismSpecies.WOLF);
//        int number = ThreadLocalRandom.current().nextInt(menu.length);
//        String menu1 = menu[number].toString();
//        System.out.println(menu1);
//        Class<? extends Organism> organismClass = OrganismSpecies.valueOf(menu1).getOrganismClass();
//        System.out.println(organismClass);
    }
}