package com.javarush.projects.model.metadata.predator_menu;

public enum BoarMenu implements Menu {
    CATERPILLAR(90),
    MOUSE(50);
    private int chancesToEat;

    BoarMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
