package com.epam.battleship.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board extends Grid {
	
	private final int sqrtShapeMatrixSize = 4;
	private Position[] grid;
	private List<Ship> fleet = new ArrayList<>();
	
	public Board() {
		grid = new Position[GRIDSIZE];
		fillGridWithBlankPositions();
	}
	
	private void fillGridWithBlankPositions() {
		for (int i = 0; i < GRIDSIZE; i++) {
			grid[i] = new Position();
		}
	}
	
	public void resetBoard() {
		for (int i = 0; i < GRIDSIZE; i++) {
			grid[i].resetPosition();
			fleet.clear();
		}
	}
	
	public PositionStatus receiveShot(final int position) {
		return grid[position].addShot();
	}
	
	public Position[] getGrid() {
		return grid;
	}
	
	public boolean isFleetDestroyed() {
		boolean result = true;
		Iterator<Ship> shipIterator = fleet.iterator();
		while (shipIterator.hasNext() && result) {
			Ship ship = shipIterator.next();
			if (ship.isAlive()) {
				result = false;
			}
		}
		
        return result;
    }
	
	public boolean isShipPlaceable(final Ship ship, final int gridReferencePosition) {
        boolean result = true;
        int[] shapeMatrix = ship.getShapeMatrix();

        for (int i = 0; i < shapeMatrix.length; i++) {
            int position = calculatePosition(i, gridReferencePosition);
            int newLine = i % sqrtShapeMatrixSize;
            if (shapeMatrix[i] == 1 && !(isLegalPosition(position, newLine) && isFreePosition(position))) {
                result = false;
            }
        }

        return result;
    }
	
	private boolean isLegalPosition(final int position, final int newLine) {
		boolean result = position < GRIDSIZE;
		if (newLine > 0) {
			result = result && ((position - 1) / GRIDWIDTH == position / GRIDWIDTH);
		}
		return result;
	}
	
	private boolean isFreePosition(final int position) {
		return grid[position].getShip() == null;
	}

    public void placeShipIntoPosition(final Ship ship, final int gridReferencePosition) {
    	int[] shapeMatrix = ship.getShapeMatrix();

        for (int i = 0; i < shapeMatrix.length; i++) {
            int position = calculatePosition(i, gridReferencePosition);
            if (shapeMatrix[i] != 0) {
                grid[position].setShip(ship);
                grid[position].setPrintValue(PositionStatus.SHIP);
            }
        }
        
        fleet.add(ship);
    }

    private int calculatePosition(int value, int gridReferencePosition) {
        int x = value % sqrtShapeMatrixSize;
		int y = value / sqrtShapeMatrixSize;
		return x + y * GRIDWIDTH + gridReferencePosition;
    }
}
