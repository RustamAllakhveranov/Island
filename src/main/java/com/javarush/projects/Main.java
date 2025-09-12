package com.javarush.projects;

public class Main {
    public static void main(String[] args) {
        Island.createIsland();
        Wolf wolf = new Wolf(0,0);
        new Rabbit(0, 1);
        new Rabbit(0, 2);
        new Rabbit(1, 0);
        new Rabbit(1, 1);
        new Rabbit(1, 2);
        new Rabbit(2, 2);
        for (int i = 0; i < 100; i++) {
            wolf.navigate();
            wolf.hunt();
        }
    }
}