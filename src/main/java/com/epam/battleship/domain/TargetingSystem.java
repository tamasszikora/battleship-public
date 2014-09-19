package com.epam.battleship.domain;

import java.util.Arrays;

public class TargetingSystem extends Grid {

	private PositionStatus[] targetGrid;
	private int targetPosition;
	private boolean targetingStrategySwitch = true;

	public TargetingSystem() {
		targetGrid = new PositionStatus[gridSize];
		Arrays.fill(targetGrid, PositionStatus.UNTOUCHED);
	}
	
	public void resetTargetingSystem() {
		Arrays.fill(targetGrid, PositionStatus.UNTOUCHED);
	}

	public PositionStatus[] getTargetGrid() {
		return targetGrid;
	}
	
	public int getTargetPosition() {
		return targetPosition;
	}
	
	public void setTargetPosition(final int position) {
		this.targetPosition = position;
	}
	
	public void setShotResultOnPosition(final int position, final PositionStatus shotResult) {
		targetGrid[position] = shotResult;
	}
	
	public boolean setTarget() {
		boolean foundClearTarget = setTargetNextToHit();
		
		if (!foundClearTarget && targetingStrategySwitch) {
			foundClearTarget = setTargetDiagonally();
			targetingStrategySwitch = !targetingStrategySwitch;
		}

		if (!foundClearTarget && !targetingStrategySwitch) {
			foundClearTarget = setTargetRandomly();
			targetingStrategySwitch = !targetingStrategySwitch;
		}
		return foundClearTarget;
	}
	
	private boolean setTargetNextToHit() {
		boolean result = false;
		int index = 0;
		while (index < (gridSize - 2) && !result) {
			if (PositionStatus.UNTOUCHED.equals(targetGrid[index]) && PositionStatus.HIT.equals(targetGrid[index + 1])) {
				targetPosition = index;
				result = true;
			} else if (PositionStatus.HIT.equals(targetGrid[index]) && PositionStatus.UNTOUCHED.equals(targetGrid[index + 1])) {
				targetPosition = index + 1;
				result = true;
			} else if (isSubmarine(index)) {
				targetPosition = index - gridWidth + 1;
				result = true;
			}

			index++;
		}
		return result;
	}

	private boolean isSubmarine(final int index) {
		return (index + 2) < gridSize && targetPosition > gridWidth
				&& PositionStatus.HIT.equals(targetGrid[index])
				&& PositionStatus.HIT.equals(targetGrid[index + 1])
				&& PositionStatus.HIT.equals(targetGrid[index + 2])
				&& PositionStatus.UNTOUCHED.equals(targetGrid[index - gridWidth + 1]);
	}

	private boolean setTargetDiagonally() {
		boolean result = false;
		int y = 0;
		int x = 0;
		while (y < gridSize && !result) {
			x = y % 2;
			while (x < gridWidth && !result) {
				targetPosition = x + y * gridWidth;
				if (PositionStatus.UNTOUCHED.equals(targetGrid[targetPosition])) {
					result = true;
				} else {
					x += 3;
				}
			}
			y++;
		}
		return result;
	}

	private boolean setTargetRandomly() {
		int x;
		int y;
		while (!PositionStatus.UNTOUCHED.equals(targetGrid[targetPosition])) {
			x = (int) (Math.random() * (gridWidth - 1));
			y = (int) (Math.random() * (gridHeight - 1));
			targetPosition = x + y * gridWidth;
		}
		return PositionStatus.UNTOUCHED.equals(targetGrid[targetPosition]);
	}
}