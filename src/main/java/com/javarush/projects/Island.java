package com.javarush.projects;

public class Island {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static Animal[][] island = new Animal[WIDTH][HEIGHT];

    public static Animal[][] getIsland() {
        return island;
    }


}
