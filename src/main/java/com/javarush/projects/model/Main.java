package com.javarush.projects.model;

import com.javarush.projects.model.organisms.Rabbit;
import com.javarush.projects.model.organisms.Wolf;
import com.javarush.projects.model.island.Island;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Island island = Island.getInstance();
        List<Thread> threadList = new ArrayList<>();
        threadList.add(new Thread(new Wolf(0, 0)));
        for (int i = 0; i < 3; i++)
            threadList.add(new Thread(new Rabbit(0, i)));
        for (Thread thread : threadList)
            thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        for (Thread thread : threadList)
            thread.interrupt();
    }
}