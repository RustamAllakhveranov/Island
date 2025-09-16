package com.javarush.projects.model.island;

//Pattern: Class Holder Singleton
public class Island {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private final Sector[][] island;

    private Island() {
        island = new Sector[WIDTH][HEIGHT];
        splitIslandToSectors();
    }

    private void splitIslandToSectors() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                island[x][y] = new Sector(x, y);
            }
        }
    }
    //синхронизация не требуется
    public Sector getSector(int positionX, int positionY) {
        return island[positionX][positionY];
    }

    private static final class InstanceHolder {
        private static final Island INSTANCE = new Island();
    }
    //синхронизация не требуется
    public static Island getInstance() {
        return InstanceHolder.INSTANCE;
    }
}