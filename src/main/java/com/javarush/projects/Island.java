package com.javarush.projects;

public class Island {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static Sector[][] island = new Sector[WIDTH][HEIGHT];

    public static Sector[][] getIsland() {
        return island;
    }


}
