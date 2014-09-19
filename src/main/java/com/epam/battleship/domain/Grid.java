package com.epam.battleship.domain;

public abstract class Grid {
	protected final int gridWidth = 10;
	protected final int gridHeight = 10;
	protected final int gridSize = 100;
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getGridHeight() {
		return gridHeight;
	}
	
	public int getGridSize() {
		return gridSize;
	}
}
