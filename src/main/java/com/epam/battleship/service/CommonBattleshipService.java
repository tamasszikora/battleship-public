package com.epam.battleship.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.battleship.domain.PositionStatus;
import com.epam.battleship.domain.Board;
import com.epam.battleship.domain.CPU;
import com.epam.battleship.domain.Human;
import com.epam.battleship.domain.Position;
import com.epam.battleship.domain.Ship;
import com.epam.battleship.domain.TargetingSystem;
import com.epam.battleship.repository.ShipLoader;
import com.epam.battleship.repository.ShipRepository;

@Service
public class CommonBattleshipService {

	@Autowired private ShipRepository shipRepository;
	@Autowired private ShipLoader shipLoader;
	
	public Human createHuman() {
		Human human = new Human();
		human.setBoard(new Board());
		human.setTargetingSystem(new TargetingSystem());
		human.setPlaceableShips(shipRepository.getShips());
		return human;
	}
	
	public CPU createCPU() {
		CPU cpu = new CPU();
		cpu.setBoard(new Board());
		cpu.setTargetingSystem(new TargetingSystem());
		cpu.setPlaceableShips(shipRepository.getShips());
		return cpu;
	}
	
	public String PlayerGridToJSON(final Position[] playerGrid) {
		int positionCounter = 0;
		StringBuilder builder = new StringBuilder("{\"positions\": [");
		for (Position position : playerGrid) {
			builder.append("{\"className\": \"" + getCubeCSSClass(position.getPrintValue()) + "\"}");
//			builder.append("\"position\": " + position.toJSON());
//			builder.append("}");
			if (positionCounter < playerGrid.length - 1) {
				builder.append(", ");
			}
			positionCounter++;
		}
		builder.append("]}");
		return builder.toString();
	}
	
	public String EnemyGridToJSON(final PositionStatus[] enemyGrid) {
		int positionCounter = 0;
		StringBuilder builder = new StringBuilder("{\"positions\": [");
		for (PositionStatus shotResult : enemyGrid) {
			builder.append("{\"className\": \"" + getCubeCSSClass(shotResult) + "\"}");
			if (positionCounter < enemyGrid.length - 1) {
				builder.append(", ");
			}
			positionCounter++;
		}
		builder.append("]}");
		return builder.toString();
	}
	
	public String placeableShipsToJSON(final List<Ship> placeableShips) {
		int shipCounter = 0;
		StringBuilder builder = new StringBuilder("{\"ships\": [");
		for (Ship ship : placeableShips) {
			builder.append("{\"id\": \"" + ship.getName() + "\", ");
			builder.append("\"shapeMatrix\": [");
			for (int i = 0; i < 16; i++) {
				builder.append(ship.getShapeMatrix()[i]);
				if (i < 15) {
					builder.append(", ");
				} 
			}
			builder.append("]");
			builder.append("}");
			if (shipCounter < placeableShips.size() - 1) {
				builder.append(", ");
			}
			shipCounter++;
		}
		builder.append("]}");
		return builder.toString();
	}
	
	public String getCubeCSSClass(final PositionStatus shotResult) {
		String result = "water";
		if (!PositionStatus.UNTOUCHED.equals(shotResult)) {
			result = shotResult.name().toLowerCase();
		}
		return result;
	}
	
	public String getElapsedTime(final long startTime) {
		Date now = new Date();
		long elapsedTime = now.getTime() - startTime;
		long min = elapsedTime / 60000;
		long sec = (elapsedTime - min * 60000) / 1000;
		return formatElapsedTime(min, sec);
	}
	
	private String formatElapsedTime(final long min, final long sec) {
		return (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
	}
}
