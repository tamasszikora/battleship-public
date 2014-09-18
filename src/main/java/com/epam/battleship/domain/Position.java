package com.epam.battleship.domain;

/**
 * Represents a position of the vector of the player's own "grid".
 * @author Tamas_Szikora
 *
 */
public class Position {
    private Ship ship;
    private PositionStatus printValue = PositionStatus.UNTOUCHED;

    public void resetPosition() {
    	ship = null;
    	printValue = PositionStatus.UNTOUCHED;
    }
    
    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public PositionStatus getPrintValue() {
        return printValue;
    }

    public void setPrintValue(final PositionStatus printValue) {
        this.printValue = printValue;
    }

    public PositionStatus addShot() {
    	PositionStatus shotResult = PositionStatus.MISS;
    	if (PositionStatus.UNTOUCHED.equals(printValue) || PositionStatus.SHIP.equals(printValue)) {
    		printValue = PositionStatus.MISS;
	        if (ship != null) {
	            printValue = PositionStatus.HIT;
	            shotResult = ship.addHit();
	        }
    	} else {
    		shotResult = printValue;
    	}

        return shotResult;
    }

	@Override
	public String toString() {
		return "POSITION[ship: " + ship + ", printValue:" + printValue + "]";
	}
    
	public String toJSON() {
    	return "{\"ship\": " + (ship == null ? "{\"name\": null}" : ship.toJSON()) + ", \"printValue\": \"" + printValue.ordinal() + "\"}";
    }
}