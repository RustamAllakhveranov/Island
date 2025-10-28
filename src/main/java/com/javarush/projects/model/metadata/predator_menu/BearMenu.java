package com.javarush.projects.model.metadata.predator_menu;

public enum BearMenu implements Menu{
    BOA(80),
    BUFFALO(20),
    DEER(80),
    DUCK(10),
    GOAT(70),
    HORSE(40),
    MOUSE(90),
    RABBIT(80),
    SHEEP(70);
    private int chancesToEat;

    BearMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
