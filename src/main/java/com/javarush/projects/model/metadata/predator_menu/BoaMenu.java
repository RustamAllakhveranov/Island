package com.javarush.projects.model.metadata.predator_menu;

public enum BoaMenu implements Menu{

    DUCK(10),
    FOX(15),
    MOUSE(40),
    RABBIT(20);

    private int chancesToEat;

    BoaMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
