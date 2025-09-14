package com.javarush.projects;

public class Main {
    public static void main(String[] args) {
        Island island = Island.getInstance();
        Wolf wolf = new Wolf(0, 0);
        new Thread(new Rabbit(0, 1)).start();
        new Thread(new Rabbit(0, 2)).start();
        new Thread(new Rabbit(1, 0)).start();
        new Thread(new Rabbit(1, 1)).start();
        new Thread(new Rabbit(1, 2)).start();
        new Thread(new Rabbit(2, 2)).start();
        Thread wolfThread = new Thread(wolf);
        wolfThread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        wolfThread.interrupt();
    }
}