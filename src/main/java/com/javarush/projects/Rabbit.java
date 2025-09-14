package com.javarush.projects;

public class Rabbit extends Animal {
    public Rabbit(int positionX, int positionY) {
        super(positionX, positionY, 2);
        weight = 2;
    }

    @Override
    public void run() {
        System.out.println(this);
        launchLifeCycle();
    }

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
