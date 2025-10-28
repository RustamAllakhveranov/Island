package com.javarush.projects.model.metadata.predator_menu;

public enum DuckMenu  implements Menu {
    CATERPILLAR(90);
    private int chancesToEat;

    DuckMenu(int chancesToEat) {
        this.chancesToEat = chancesToEat;
    }
}
