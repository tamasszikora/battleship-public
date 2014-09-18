package com.epam.battleship.controller;

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
import com.epam.battleship.domain.Player;
import com.epam.battleship.domain.User;
import com.epam.battleship.service.CommonBattleshipService;
import com.epam.battleship.service.RemoteBattleshipService;

@Controller
public class PlaceShipController {

	@Autowired private RemoteBattleshipService remoteService;
	@Autowired private CommonBattleshipService commonService;

	@RequestMapping(value = "/local/playBattle", method = RequestMethod.POST)
	public String startLocalBattle(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Player cpu = (Player) session.getAttribute("cpu");
		cpu.setPlaceableShips(user.getPlacedShips());
		cpu.placeShipsRandomly();
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		return "gamePlay";
	}
	
	@RequestMapping(value = "/remote/setPlayerReady", method = RequestMethod.GET)
	public @ResponseBody String setPlayerReady(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int battleId = (int) session.getAttribute("battleId");
		remoteService.setBattleStatus(battleId, BattleStatus.PLACE_SHIPS_READY);
		return remoteService.getBattleStatusById(battleId).name();
	}

	@RequestMapping(value = "/remote/playBattle", method = RequestMethod.GET)
	public String startRemoteBattle(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
//		int battleId = (int) session.getAttribute("battleId");
//		remoteService.setBattleStatus(battleId, BattleStatus.PLAYER_ONE_TURN);
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		return "gamePlay";
	}
	
	@RequestMapping(value = "/placeShipsRandomly", method = RequestMethod.POST)
	public String localPlaceShipsRandomly(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.placeShipsRandomly();
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		return session.getAttribute("gameMode") + "PlaceShips";
	}

	@RequestMapping(value = "/placeShip", method = RequestMethod.GET)
	public @ResponseBody String placeShip(@RequestParam final String name, @RequestParam final int position, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user.isShipPlaceable(name, position)) {
			user.placeShipIntoPosition(name, position);
		}
		return "";
	}
}
