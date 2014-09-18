package com.epam.battleship.domain;

public class CPU extends Player {
	public boolean setTarget() {
		return targetingSystem.setTarget();
	}
	
	public int getTargetPosition() {
		return targetingSystem.getTargetPosition();
	}
}
