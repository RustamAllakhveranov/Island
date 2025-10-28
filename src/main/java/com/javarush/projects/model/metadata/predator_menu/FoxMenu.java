package com.javarush.projects.model.metadata.predator_menu;

public enum FoxMenu implements Menu  {
    CATERPILLAR(40),
    DUCK(60),
    MOUSE(90),
    RABBIT(70);
    private int chancesToEat;

    FoxMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
