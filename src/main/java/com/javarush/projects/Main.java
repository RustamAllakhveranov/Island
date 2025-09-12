package com.javarush.projects;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal(0, 0);
        for (int i = 0; i < 10; i++) {
            animal.navigate();
        }
        Animal[][] island = Island.getIsland();
        for (Animal[] islandX : island) {
            for (Animal animal1 : islandX) {
                if (animal1 instanceof Animal) {
                    System.out.println(animal1);
                }
            }
        }
    }
}