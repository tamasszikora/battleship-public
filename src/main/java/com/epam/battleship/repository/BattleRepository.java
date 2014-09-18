package com.epam.battleship.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.epam.battleship.domain.Battle;
import com.epam.battleship.domain.BattleStatus;
import com.epam.battleship.domain.User;

@Repository
public class BattleRepository {

	private List<Battle> battles = new ArrayList<Battle>();
	
	public void addBattle(final Battle battle) {
		battles.add(battle);
	}
	
	public void removeBattle(final Battle battle) {
		battles.remove(battle);
	}
	
	public Battle getBattleById(final int battleId) {
		return battles.get(battleId);
	}
	
	public Battle getBattleByPlayer(final User user) {
		Battle battle = null;
		for (Battle b : battles) {
			if ((b.getPlayerOne().getId().equals(user.getId()) || b.getPlayerTwo().getId().equals(user.getId()))) {
				battle = b;
			}
		}
		
		return battle;
	}
	
	public int getBattleIdByBattle(final Battle battle) {
		return battles.indexOf(battle);
	}
	
	public User getOpponent(final int battleId, final User user) {
		User opponent = null;
		Battle battle = battles.get(battleId);
		if (battle.getPlayerOne().getId() == user.getId()) {
			opponent = battle.getPlayerTwo();
		} else {
			opponent = battle.getPlayerOne();
		}
		return opponent;
	}

	public boolean isThisUserTurn(User user) {
		boolean result = false;
		for (Battle b : battles) {
			if (b.getPlayerTwo().getId().equals(user.getId()) && BattleStatus.PLAYER_TWO_TURN.equals(b.getStatus())) {
				result = true;
			} else if (b.getPlayerOne().getId().equals(user.getId()) && BattleStatus.PLAYER_ONE_TURN.equals(b.getStatus())) {
				result = true;
			}
		}
		return result;
	}
}
