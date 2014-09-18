package com.epam.battleship.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.epam.battleship.domain.Ship;

@Repository
public class ShipRepository {

	private final List<Ship> ships = new ArrayList<>();
	
	{
		int[] shape1 = {1,1,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		Ship ship1 = new Ship("Destroyer", 2, shape1);
		int[] shape2 = {1,1,1,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		Ship ship2 = new Ship("Cruiser", 3, shape2);
		int[] shape3 = {1,1,1,1, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		Ship ship3 = new Ship("Battleship", 4, shape3);
		int[] shape4 = {0,1,0,0, 1,1,1,0, 0,0,0,0, 0,0,0,0};
		Ship ship4 = new Ship("Submarine", 4, shape4);
		
		ships.add(ship1);
		ships.add(ship2);
		ships.add(ship3);
		ships.add(ship4);
	}
	
	public void addShip(final Ship ship) {
		ships.add(ship);
	}
	
	public void removeShip(final Ship ship) {
		ships.remove(ship);
	}
	
	public List<Ship> getShips() {
		return Collections.unmodifiableList(ships);
	}
}
