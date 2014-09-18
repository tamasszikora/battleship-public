package com.epam.battleship.domain;

import java.util.Arrays;

public class Ship {

	private String name;
    private int shipSize;
    private int[] shapeMatrix;
    private boolean alive = true;

    private int hitCounter;

    public Ship(final String name, final int shipSize, final int[] shapeMatrix) {
    	this.name = name;
        this.shipSize = shipSize;
        this.shapeMatrix = Arrays.copyOf(shapeMatrix, 16);
    }
    
    public Ship copyShip() {
    	return new Ship(this.name, this.shipSize, this.shapeMatrix);
    }
    
    public String getName() {
    	return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getShipSize() {
        return shipSize;
    }

    public int[] getShapeMatrix() {
        return shapeMatrix;
    }

    public void setShapeMatrix(final int[] shapeMatrix) {
        this.shapeMatrix = shapeMatrix;
    }

    public PositionStatus addHit() {
    	PositionStatus shotResult = PositionStatus.HIT;
        hitCounter++;

        if (shipSize == hitCounter) {
            alive = false;
            shotResult = PositionStatus.SUNK;
        }

        return shotResult;
    }

    private String shapeMatrixToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < shapeMatrix.length; i++) {
            builder.append(' ');
            builder.append(shapeMatrix[i]);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "SHIP[Name: " + name + ", shipsize: " + shipSize + ", shapeMatrix:" + shapeMatrixToString() + "]";
    }
    
    public String toJSON() {
    	return "{\"name\": \"" + name + "\"}";
    }
}