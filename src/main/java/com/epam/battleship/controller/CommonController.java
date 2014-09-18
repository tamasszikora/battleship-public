package com.epam.battleship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.battleship.domain.BattleStatus;
import com.epam.battleship.domain.User;
import com.epam.battleship.service.CommonBattleshipService;
import com.epam.battleship.service.RemoteBattleshipService;
import com.epam.battleship.service.UserService;

@Controller
public class CommonController {
	
	@Autowired private CommonBattleshipService commonService;
	@Autowired private UserService userService;
	@Autowired private RemoteBattleshipService remoteService;

	@RequestMapping(value = "/loadPlayerGridJSON", method = RequestMethod.GET)
	public @ResponseBody String loadPlayerGridJSON(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return commonService.PlayerGridToJSON(user.getGrid());
	}
	
	@RequestMapping(value = "/loadEnemyGrid", method = RequestMethod.GET)
	public @ResponseBody String loadEnemyGrid(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return commonService.EnemyGridToJSON(user.getTargetGrid());
	}
	
	@RequestMapping(value = "/loadPlaceableShipsJSON", method = RequestMethod.GET)
	public @ResponseBody String loadPlaceAbleShipsJSON(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return commonService.placeableShipsToJSON(user.getPlaceableShips());
	}
	
	@RequestMapping(value = "/getUserStatus", method = RequestMethod.GET)
	public @ResponseBody String getUserStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		User sessionUser = (User) session.getAttribute("user");
		String status = "INACTIVE";
		if (sessionUser != null) {
			User user = userService.getUser(sessionUser.getId());
			status = user.getStatus();
			session.setAttribute("user", user);
		}
		
		return status;
	}
	
	@RequestMapping(value = "/getBattleStatus", method = RequestMethod.GET)
	public @ResponseBody String getBattleStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BattleStatus status = BattleStatus.BATTLE_ERROR;
		Integer battleId = (Integer) session.getAttribute("battleId");
		if (battleId != null) {
			status = remoteService.getBattleStatusById(battleId);
		}

		if (BattleStatus.PLAYER_ONE_TURN.equals(status) || BattleStatus.PLAYER_TWO_TURN.equals(status)) {
			User user = (User) session.getAttribute("user");
			if (remoteService.isThisUsersTurn(user)) {
				status = BattleStatus.YOUR_TURN;
			} else {
				status = BattleStatus.NOT_YOUR_TURN;
			}
		}
		
		return status.name();
	}
	
	@RequestMapping(value = "/switchDimension", method = RequestMethod.GET)
	public @ResponseBody String switchDimension(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String threeDimension;
		if (session.getAttribute("threeDimension") == null) {
			threeDimension = "true";
		} else {
			threeDimension = String.valueOf(!Boolean.valueOf(session.getAttribute("threeDimension").toString()));
		}
		
		session.setAttribute("threeDimension", threeDimension);
		return threeDimension;
	}
}