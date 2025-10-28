package com.javarush.projects.model.metadata.predator_menu;

public enum MouseMenu implements Menu {
    CATERPILLAR(90);
    private int chancesToEat;

    MouseMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
