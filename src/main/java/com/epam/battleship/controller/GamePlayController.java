package com.epam.battleship.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.battleship.domain.BattleStatus;
import com.epam.battleship.domain.PositionStatus;
import com.epam.battleship.domain.CPU;
import com.epam.battleship.domain.Player;
import com.epam.battleship.domain.User;
import com.epam.battleship.domain.UserStatus;
import com.epam.battleship.service.CommonBattleshipService;
import com.epam.battleship.service.RemoteBattleshipService;
import com.epam.battleship.service.UserService;

@Controller
public class GamePlayController {
	@Autowired private UserService userService;
	@Autowired private RemoteBattleshipService remoteService;
	@Autowired private CommonBattleshipService commonService;
	
	@RequestMapping(value = "/addFire", method = RequestMethod.GET)
	public @ResponseBody String addFire(@RequestParam final int position, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		PositionStatus shotResult = PositionStatus.MISS;
		
		if ("local".equals(session.getAttribute("gameMode").toString())) {
			User user = (User) session.getAttribute("user");
			CPU cpu = (CPU) session.getAttribute("cpu");
			shotResult = cpu.receiveShot(position);
			if (!PositionStatus.MISS.equals(shotResult)) {
				shotResult = PositionStatus.HIT;
			}
			user.setShotResultOnPosition(position, shotResult);
			
			System.out.println("addFire send: FIRE " + position);
			System.out.println("addFire receive: " + shotResult.name());
			receiveFireLocal(user, cpu);
		} else {
			User user = (User) session.getAttribute("user");
			if (remoteService.isThisUsersTurn(user)) {
				int battleId = (int) session.getAttribute("battleId");
				User opponent = remoteService.getOpponentFromBattle(battleId, user);
				//User opponent = remoteService.getBattleById(battleId).getPlayerTwo();
				shotResult = opponent.receiveShot(position);
				remoteService.changeBattlePlayerTurn((int) session.getAttribute("battleId"));
			} else {
				shotResult = PositionStatus.NOT_YOUR_TURN;
			}
		}
		return commonService.getCubeCSSClass(shotResult);
	}

	private void receiveFireLocal(final User user, final CPU cpu) {
		cpu.setTarget();
		int position = cpu.getTargetPosition();
		PositionStatus shotResult = user.receiveShot(position);
		cpu.setShotResultOnPosition(position, shotResult);
		System.out.println("receiveFire receive: FIRE " + position);
		System.out.println("receiveFire send: " + shotResult.name());
	}
	
	@RequestMapping(value = "/isPlayerFleetDestroyed", method = RequestMethod.GET)
	public @ResponseBody String isPlayerFleetDestroyed(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return String.valueOf(user.isFleetDestroyed());
	}
	
	@RequestMapping(value = "/isEnemyFleetDestroyed", method = RequestMethod.GET)
	public @ResponseBody String isCPUFleetDestroyed(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String result = "false";
		if ("local".equals(session.getAttribute("gameMode").toString())) {
			Player cpu = (Player) session.getAttribute("cpu");
			result = String.valueOf(cpu.isFleetDestroyed());
		} else {
			User user = (User) session.getAttribute("user");
			int battleId = (int) session.getAttribute("battleId");
			User opponent = remoteService.getOpponentFromBattle(battleId, user);
			result = String.valueOf(opponent.isFleetDestroyed());
		}
		return result;
	}
	
	@RequestMapping(value = "/setStartTime", method = RequestMethod.GET)
	public @ResponseBody String setStartTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Date now = new Date();
		session.setAttribute("startTime", now.getTime());
		return "";
	}
	
	@RequestMapping(value = "/getElapsedTime", method = RequestMethod.GET)
	public @ResponseBody String getElapsedTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		long startTime = (long) session.getAttribute("startTime");
		return commonService.getElapsedTime(startTime);
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String resetGame(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer battleId = (Integer) session.getAttribute("battleId");
		if (battleId != null) {
			remoteService.setBattleStatus(battleId, BattleStatus.PLAYER_ONE_WON);
		}
		User sessionUser = (User) session.getAttribute("user");
		userService.setUserStatus(sessionUser, UserStatus.ACTIVE);
		session.setAttribute("user", sessionUser);
		session.setAttribute("battleId", null);
		
		return "redirect:/";
	}
}