package com.epam.battleship.domain;

public abstract class Grid {
	protected final int GRIDWIDTH = 10;
	protected final int GRIDHEIGHT = 10;
	protected final int GRIDSIZE = 100;
	
	public int getGridWidth() {
		return GRIDWIDTH;
	}
	
	public int getGridHeight() {
		return GRIDHEIGHT;
	}
	
	public int getGridSize() {
		return GRIDSIZE;
	}
}
