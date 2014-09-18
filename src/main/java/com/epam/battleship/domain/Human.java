package com.epam.battleship.domain;

import java.util.Collections;
import java.util.List;

public class Human extends Player {
	
	public Position[] getGrid() {
		return board.getGrid();
	}
	
	public PositionStatus[] getTargetGrid() {
		return targetingSystem.getTargetGrid();
	}
	
	public List<Ship> getPlaceableShips() {
		return Collections.unmodifiableList(placeableShips);
	}
	
	public void addPlaceableShip(final Ship ship) {
		this.placeableShips.add(ship.copyShip());
	}
	
	public List<Ship> getPlacedShips() {
		return Collections.unmodifiableList(placedShips);
	}
}
