package com.javarush.projects;

public class Island {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static Sector[][] island = new Sector[WIDTH][HEIGHT];



    public static void populateIsland() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                island[x][y] = new Sector(x, y);
            }
        }
    }

    public static Sector getSector(int positionX, int positionY) {
        return island[positionX][positionY];
    }
}
