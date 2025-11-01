package com.javarush.projects.controller;

import com.javarush.projects.model.organisms.Bear;

public class StandardBiotaBuilder implements BiotaBuilder {
    private Biota biota;

    public StandardBiotaBuilder() {
         reset();
    }

    private void reset() {
        this.biota = new Biota();
    }


    public  void createBiota() {
        biota = new Biota();
    }
    public Biota getResult(){
        return biota;
    }

    @Override
    public void addBears(int count) {
        for (int i = 0; i < count; i++) {
            biota.addOrganism(new Bear(1,3));
        }
    }

    @Override
    public void addBoas(int count) {

    }

    @Override
    public void addBoars(int count) {

    }

    @Override
    public void addBuffalos(int count) {

    }

    @Override
    public void addCaterpillars(int count) {

    }

    @Override
    public void addDeers(int count) {

    }

    @Override
    public void addDucks(int count) {

    }

    @Override
    public void addEagles(int count) {

    }

    @Override
    public void addFoxes(int count) {

    }

    @Override
    public void addGoats(int count) {

    }

    @Override
    public void addHorses(int count) {

    }

    @Override
    public void addMouses(int count) {

    }

    @Override
    public void addPlants(int count) {

    }

    @Override
    public void addRabbits(int count) {

    }

    @Override
    public void addSheeps(int count) {

    }

    @Override
    public void addWolves(int count) {

    }
}

