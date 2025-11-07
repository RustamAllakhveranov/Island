package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;
import com.javarush.projects.model.island.Sector;

public class Plant extends Organism {
    public Plant(Sector startPosition) {
        super(startPosition);
    }

    @Override
    protected void launchLifeCycle() {
        currentLocation.addPlant(this);
    }

    @Override
    protected  void eraseBody() {
        boolean removed = currentLocation.removePlant(this);
        synchronized (this) {
            if (removed) {
                LOGGER.debug("{} erased from {}", this, currentLocation);
                currentLocation = null;
            } else {
                String message = String.format("%s wasn't erased from %s", this, currentLocation);
                LOGGER.error(message);
                throw new ErasingException(message);
            }
        }
        die();
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