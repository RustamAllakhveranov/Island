package com.javarush.projects.model.metadata.predator_menu;

public enum EagleMenu implements Menu  {
    DUCK(80),
    FOX(10),
    MOUSE(90),
    RABBIT(90);
    private int chancesToEat;

    EagleMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
