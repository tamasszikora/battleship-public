package com.epam.battleship.domain;

public class Battle {

	private User playerOne;
	private User playerTwo;
	private BattleStatus status = BattleStatus.REQUEST_SENT;

	public Battle(final User playerOne, final User playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public BattleStatus getStatus() {
		return status;
	}

	public void setStatus(final BattleStatus status) {
		if (BattleStatus.PLACE_SHIPS_READY.equals(status) && BattleStatus.PLACE_SHIPS_READY.equals(this.status)) {
			this.status = BattleStatus.PLAYER_ONE_TURN;
		} else {
			this.status = status;
		}
	}
	
	public void setPlayer(User user) {
		if (playerOne.getId() == user.getId()) {
			playerOne = user;
		} else {
			playerTwo = user;
		}
	}

	public User getPlayerOne() {
		return playerOne;
	}
	
	public User getPlayerTwo() {
		return playerTwo;
	}

	@Override
	public String toString() {
		return "Battle [playerOne=" + playerOne + ", playerTwo=" + playerTwo
				+ ", status=" + status + "]";
	}
}
