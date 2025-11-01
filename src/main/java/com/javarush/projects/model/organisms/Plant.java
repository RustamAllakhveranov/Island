package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;

public class Plant extends Organism {
    public Plant(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    protected void launchLifeCycle() {

    }
    @Override
    protected void eraseBody() {
        boolean removed = currentLocation.removePlant(this);
        if (removed) {
            LOGGER.debug("{} erased from {}", this, currentLocation);
        } else {
            throw new ErasingException(String.format("%s wasn't erased from %s", this, currentLocation));
        }
    }

//    public void grow() {
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            bodyWeight.add(getBodyWeight() / 10);
//
//        }
//    }
}