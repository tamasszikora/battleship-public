package com.epam.battleship.controller;

import java.util.Arrays;

public class ShipForm {
	private String name;
	private int size;
	private int[] shapeMatrix = new int[16];
	
	public ShipForm() {
		Arrays.fill(shapeMatrix, Integer.valueOf(0));
	}
	
	public void resetShipForm() {
		name = null;
		size = 0;
		Arrays.fill(shapeMatrix, Integer.valueOf(0));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public int getSize() {
		return size;
	}
	
	public int[] getShapeMatrix() {
		return shapeMatrix;
	}
	
	public int  changeShapeMatrix(final int index) {
		if (shapeMatrix[index] == 0) {
			shapeMatrix[index] = 1;
			size++;
		} else {
			shapeMatrix[index] = 0;
			size--;
		}
		return shapeMatrix[index];
	}
}
