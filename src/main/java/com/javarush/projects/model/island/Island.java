package com.javarush.projects.model.island;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Pattern: Class Holder Singleton
public class Island {
    private static final Logger LOGGER = LoggerFactory.getLogger(Island.class.getName());
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private final Sector[][] islandSectors;

    private Island() {
        LOGGER.info("Начало создания острова. Размеры: {} x {}", WIDTH, HEIGHT);
        islandSectors = new Sector[WIDTH][HEIGHT];
        splitIslandToSectors();
        LOGGER.info("Остров создан!");
    }

    private void splitIslandToSectors() {
        LOGGER.trace("Начало инициализации секторов.");
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                islandSectors[x][y] = new Sector(x, y);
                LOGGER.trace("Инициализирован сектор[{}][{}]", x, y);
            }
        }
        LOGGER.trace("Все сектора инициализированы.");
    }
    //синхронизация не требуется
    //логирование не требуется
    public Sector getSector(int positionX, int positionY) {
        return islandSectors[positionX][positionY];
    }

    private static final class InstanceHolder {
        private static final Island INSTANCE = new Island();
    }
    //синхронизация не требуется
    //логирование не требуется
    public static Island getInstance() {
        return InstanceHolder.INSTANCE;
    }
}