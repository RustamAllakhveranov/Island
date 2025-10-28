package com.javarush.projects.model.organisms;

import com.javarush.projects.model.exceptions.ErasingException;

public class Plant extends Organism {
    public Plant(int positionX, int positionY) {
        super(positionX, positionY);
        currentLocation.addPlant(this);
        bodyWeight.add(1);
    }


    @Override
    protected void launchLifeCycle() {

    }

    @Override
    protected synchronized void die() {
        boolean erased = currentLocation.getPlants().remove(this);
        if (erased) {
            LOGGER.debug("{} erased from {}", this, currentLocation);
        } else {
            throw new ErasingException(String.format("%s wasn't erased from %s", this, currentLocation));
        }
        individualThread.interrupt();
    }

    public void grow() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bodyWeight.add(getBodyWeight() / 10);

        }
    }
}