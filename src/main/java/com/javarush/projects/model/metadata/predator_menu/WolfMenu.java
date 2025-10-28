package com.javarush.projects.model.metadata.predator_menu;

public enum WolfMenu implements Menu {
    BOAR(15),
    BUFFALO(10),
    DEER(15),
    DUCK(40),
    GOAT(60),
    HORSE(10),
    MOUSE(80),
    RABBIT(60),
    SHEEP(70);
    private int chancesToEat;

    WolfMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
