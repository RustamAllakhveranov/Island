package com.javarush.projects.model.organisms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wolf extends Animal implements Predator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wolf.class);


    public Wolf(int positionX, int positionY) {
        super(positionX, positionY, 3);
        weight.add(50);
    }

    //вроде как синхронизация не нужна, она есть внутри метода die()
    @Override
    public void eatAnimal(Animal animal) {
        animal.die();
        LOGGER.info("{} cъедает {}", this, animal);
    }

    //проверка двух условий должна происходить синхронно, иначе будут косяки
    public void hunt() {
        LOGGER.info("{} выходит на охоту!", this);
        Rabbit rabbit = currentLocation.getRabbit();
        synchronized (this) {
            if (rabbit != null && rabbit.isAlive) {
                LOGGER.info("{} убивает {}", this, rabbit);
                rabbit.die();
            }
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