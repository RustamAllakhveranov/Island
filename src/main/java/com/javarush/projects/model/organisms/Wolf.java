package com.javarush.projects.model.organisms;

public class Wolf extends Animal implements Predator {

    public Wolf(int positionX, int positionY) {
        super(positionX, positionY, 3);
        weight = 50;
    }

    public void eatRabbit(Rabbit rabbit) {
        rabbit.die();
    }

    @Override
    public void eatAnimal(Animal animal) {
        animal.die();
    }

    public void hunt() {
        Rabbit rabbit = currentLocation.getRabbit();
        if (rabbit != null && rabbit.isAlive) {
            rabbit.die();
            System.out.println("RABBIT KILLED!!!");
        }
    }


    protected void launchLifeCycle() {
        System.out.println(this);
        while (!Thread.currentThread().isInterrupted()) {
            hunt();
            navigate();
        }
    }
}