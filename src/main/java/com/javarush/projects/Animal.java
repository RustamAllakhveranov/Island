package com.javarush.projects;

public class Animal {
    private int positionX;
    private int positionY;

    public Animal(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void changePosition(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
        Island.getIsland()[positionX][positionY] = this;
    }
    
}
