package com.epam.battleship.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
	protected Board board;
	protected TargetingSystem targetingSystem;
	protected List<Ship> placeableShips = new ArrayList<>();
	protected List<Ship> placedShips = new ArrayList<>();
	
	public void setBoard(final Board board) {
		this.board = board;
	}
	
	public void setTargetingSystem(final TargetingSystem targetingSystem) {
		this.targetingSystem = targetingSystem;
	}
	
	public void setPlaceableShips(final List<Ship> placeableShips) {
		this.placeableShips.clear();
		for (Ship ship : placeableShips) {
			this.placeableShips.add(ship.copyShip());
		}
	}
	
	public void placeShipsRandomly() {
		int gridSize = board.getGridSize();
		boolean isShipPlaceable;
		int position = (int) (Math.random() * (gridSize - 1));
		List<Ship> copyOfPlaceableShips = new ArrayList<Ship>(placeableShips);
        for (Ship ship : copyOfPlaceableShips) {
        	isShipPlaceable = isShipPlaceable(ship.getName(), position);
            while (!isShipPlaceable) {
                position = (int) (Math.random() * (gridSize - 1));
                isShipPlaceable = isShipPlaceable(ship.getName(), position);
            }
            if (isShipPlaceable) {
            	placeShipIntoPosition(ship.getName(), position);
            }
        }
	}
	
	public boolean isShipPlaceable(final String name, final int position) {
		boolean result = false;
		Ship ship = findShipByName(name);
		result = findShipByName(name) == null ? false : true;
		if (result) {
			result = board.isShipPlaceable(ship, position);
		}
		
		return result;
	}
	
	public void placeShipIntoPosition(final String name, final int position) {
		Ship ship = findShipByName(name);
		if (ship != null) {
			board.placeShipIntoPosition(ship, position);
			placedShips.add(ship);
			placeableShips.remove(ship);
		}
	}
	
	private Ship findShipByName(final String name) {
		Ship result = null; 
		if (name != null && !name.isEmpty()) {
			Iterator<Ship> iterator = placeableShips.iterator();
			
			Ship ship = null;
			while (iterator.hasNext() && result == null) {
				ship = iterator.next();
				if (name.equals(ship.getName())) {
					result = ship;
				}
			}
		}
		return result;
	}
	
	public void setShotResultOnPosition(final int position, final PositionStatus shotResult) {
		targetingSystem.setShotResultOnPosition(position, shotResult);
	}
	
	public PositionStatus receiveShot(final int position) {
		return board.receiveShot(position);
	}
	
	public boolean isFleetDestroyed() {
		return board.isFleetDestroyed();
	}
}
