package com.epam.battleship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.battleship.domain.Battle;
import com.epam.battleship.domain.BattleStatus;
import com.epam.battleship.domain.User;
import com.epam.battleship.repository.BattleRepository;
import com.epam.battleship.repository.ShipLoader;
import com.epam.battleship.repository.ShipRepository;

@Service
public class RemoteBattleshipService {
	@Autowired private ShipRepository shipRepository;
	@Autowired private ShipLoader shipLoader;
	@Autowired private BattleRepository battleRepository;
	
	public int addBattle(User playerOne, User playerTwo) {
		Battle battle = new Battle(playerOne, playerTwo);
		battleRepository.addBattle(battle);
		return battleRepository.getBattleIdByBattle(battle);
	}
	
	public int acceptBattleRequest(User user) {
		Battle battle = battleRepository.getBattleByPlayer(user);
		battle.setStatus(BattleStatus.PLACE_SHIPS);
		return battleRepository.getBattleIdByBattle(battle);
	}
	
	public void declineBattleRequest(User user) {
		Battle battle = battleRepository.getBattleByPlayer(user);
		if (BattleStatus.REQUEST_DECLINED.equals(battle.getStatus())) {
			battleRepository.removeBattle(battle);
		} else {
			battle.setStatus(BattleStatus.REQUEST_DECLINED);
		}
	}
	
	public void setBattleStatus(final int battleId, final BattleStatus status) {
		Battle battle = battleRepository.getBattleById(battleId);
		battle.setStatus(status);
	}
	
	public User getOpponentFromBattle(final int battleId, final User user) {
		return battleRepository.getOpponent(battleId, user);
	}
	
	public Battle getBattleById(int battleId) {
		return battleRepository.getBattleById(battleId);
	}
	
	public BattleStatus getBattleStatusById(int battleId) {
		return battleRepository.getBattleById(battleId).getStatus();
	}
	
//	public BattleStatus setBattleStatusReadyByPlayer(int battleId, User user) {
//		Battle battle = battleRepository.getBattleById(battleId);
//		if (BattleStatus.ONE_READY_WITH_PLACE_SHIPS.equals(battle.getStatus())) {
//			battle.setStatus(BattleStatus.START_BATTLE);
//		} else 
//		
//		
//		User playerOne = battle.getPlayerOne();
//		if (playerOne.getId() == user.getId()) {
//			if (BattleStatus.PLAYER_TWO_READY.equals(battle.getStatus())) {
//				battle.setStatus(BattleStatus.PLAYERS_READY);
//			} else {
//				battle.setStatus(BattleStatus.PLAYER_ONE_READY);
//			}
//		} else {
//			if (BattleStatus.PLAYER_ONE_READY.equals(battle.getStatus())) {
//				battle.setStatus(BattleStatus.PLAYERS_READY);
//			} else {
//				battle.setStatus(BattleStatus.PLAYER_TWO_READY);
//			}
//		}
//		
//		return battle.getStatus();
//	}
	
	public void changeBattlePlayerTurn(int battleId) {
		Battle battle = battleRepository.getBattleById(battleId);
		if (BattleStatus.PLAYER_ONE_TURN.equals(battle.getStatus())) {
			battle.setStatus(BattleStatus.PLAYER_TWO_TURN);
		} else {
			battle.setStatus(BattleStatus.PLAYER_ONE_TURN);
		}
	}
	
	public void setBattleStatusToStart(int battleId) {
		Battle battle = battleRepository.getBattleById(battleId);
		battle.setStatus(BattleStatus.PLAYER_ONE_TURN);
	}
	
	public boolean isThisUsersTurn(User user) {
		return battleRepository.isThisUserTurn(user);
	}
}
