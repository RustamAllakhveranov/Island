package com.javarush.projects;

public class Wolf extends Animal {
    public Wolf(int positionX, int positionY) {
        super(positionX, positionY, 3);
    }

    public void eatRabbit(Rabbit rabbit) {
        rabbit.die();
    }

    public void hunt() {
        Rabbit rabbit = Island.getSector(positionX, positionY).findRabbits();
        if (rabbit != null && rabbit.isAlive){
            rabbit.die();
            System.out.println("RABBIT HUNTED!!!");
        }
    }
}
