package com.javarush.projects.model.organisms;

public class Rabbit extends Animal {
    public Rabbit(int positionX, int positionY) {
        super(positionX, positionY, 2);
        weight.add(2);
    }

    //переписать однозначно
    @Override
    protected void launchLifeCycle() {
        while (!Thread.currentThread().isInterrupted()){
            navigate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
