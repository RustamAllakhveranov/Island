package com.javarush.projects.model.organisms;

public class Wolf extends Animal implements Predator {

    public Wolf(int positionX, int positionY) {
        super(positionX, positionY, 3);
        weight.add(50);
    }
//вроде как синхронизация не нужна, она есть внутри метода die()
    @Override
    public void eatAnimal(Animal animal) {
        animal.die();
    }

//проверка двух условий должна происходить синхронно, иначе будут косяки
    public void hunt() {
        Rabbit rabbit = currentLocation.getRabbit();
        if (rabbit != null && rabbit.isAlive) {
            rabbit.die();
            System.out.println("Killer of " + rabbit + " is: " + this);
        }
    }
//переписать однозначно
    protected void launchLifeCycle() {
        while (!Thread.currentThread().isInterrupted()) {
            hunt();
            navigate();
        }
    }
}