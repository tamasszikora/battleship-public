package com.epam.battleship.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.epam.battleship.domain.Ship;

/**
 * Loads and creates the Ship objects from the given source file.
 * 
 * @author Tamas_Szikora
 * 
 */
@Repository
public class ShipLoader {
	private List<Ship> shipList = new ArrayList<Ship>();

	/**
	 * Loads Ships from the given source file into a List.
	 * 
	 * @return List<Ship>
	 */
	public List<Ship> loadShips() {
		int shipCounter = 0;
		int shipSize = 0;
		while (shipCounter < 4) {
			if (shipCounter < 1) {
				shipSize = 2;
				int[] shapeMatrix = { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				shipList.add(new Ship("import ship " + shipCounter, shipSize, shapeMatrix));
			} else if (shipCounter < 2) {
				shipSize = 3;
				int[] shapeMatrix = { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				shipList.add(new Ship("import ship " + shipCounter, shipSize, shapeMatrix));
			} else if (shipCounter < 3) {
				shipSize = 4;
				int[] shapeMatrix = { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				shipList.add(new Ship("import ship " + shipCounter, shipSize, shapeMatrix));
			} else {
				shipSize = 4;
				int[] shapeMatrix = { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				shipList.add(new Ship("import ship " + shipCounter, shipSize, shapeMatrix));
			}
			
			shipCounter++;
		}

		return shipList;
	}

}
