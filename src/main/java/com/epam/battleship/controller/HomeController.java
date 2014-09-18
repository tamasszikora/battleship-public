package com.epam.battleship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.battleship.domain.Battle;
import com.epam.battleship.domain.User;
import com.epam.battleship.domain.UserStatus;
import com.epam.battleship.service.CommonBattleshipService;
import com.epam.battleship.service.RemoteBattleshipService;
import com.epam.battleship.service.UserService;

@Controller
public class HomeController {
	@Autowired private UserService userService;
	@Autowired private RemoteBattleshipService remoteService;
	@Autowired private CommonBattleshipService commonService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("threeDimension") == null) {
			session.setAttribute("threeDimension", "true");
		}
		
		String loggedInAs = "";
//		userService.cleanExpiredLogIns(getOneHourBack());
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			userService.refreshUserLogIn(user);
			loggedInAs = user.getName();
			model.addAttribute("opponents", userService.getOpponents(user));
		} else {
			model.addAttribute("users", userService.getUsers());
		}
		
		model.addAttribute("loggedInAs", loggedInAs);
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		return "home";
	}
	
//	private Date getOneHourBack() {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.add(Calendar.HOUR, -1);
//		Date oneHourBack = cal.getTime();
//		return oneHourBack;
//	}
	
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public String logIn(@RequestParam final Long userId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = userService.getUser(userId);

		if (user != null) {
			userService.setUserStatus(user, UserStatus.ACTIVE);
			session.setAttribute("user", user);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public String logOut(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			userService.setUserStatus(user, UserStatus.INACTIVE);
			session.setAttribute("user", null);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/local/gettingStarted", method = RequestMethod.GET)
	public String startLocalGame(Model model, HttpServletRequest request) {
		String pageToLoad = "localPlaceShips";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("errorText", "You must log in to play Battleship!");
			pageToLoad = "errorPage";
		} else {
			session.setAttribute("gameMode", "local");
			model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
			user.setHuman(commonService.createHuman());
			userService.setUserStatus(user, UserStatus.OCCUPIED);
			session.setAttribute("user", user);
			session.setAttribute("cpu", commonService.createCPU());
		}
		
		return pageToLoad;
	}
	
	@RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
	public String sendBattleRequest(@RequestParam final long opponentId, Model model, HttpServletRequest request) {
		String pageToLoad = "redirect:/";
		HttpSession session = request.getSession();
		
		User playerOne = (User) session.getAttribute("user");
		User playerTwo = userService.getUser(opponentId);
		if (playerOne == null || playerTwo == null) {
			model.addAttribute("errorText", "You must log in to play Battleship!");
			pageToLoad = "errorPage";
		} else {
			userService.setUserStatus(playerOne, UserStatus.OCCUPIED);
			userService.setUserStatus(playerTwo, UserStatus.REQUESTED);
			
			session.setAttribute("user", playerOne);
			session.setAttribute("battleId", remoteService.addBattle(playerOne, playerTwo));
		}
		
		return pageToLoad;
	}
	
	@RequestMapping(value = "/acceptBattleRequest", method = RequestMethod.GET)
	public String acceptBattleRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			userService.setUserStatus(user, UserStatus.OCCUPIED);
			session.setAttribute("user", user);
			session.setAttribute("battleId", remoteService.acceptBattleRequest(user));
		}
		
		return "redirect:/remote/gettingStarted";
	}
	
	@RequestMapping(value = "/declineBattleRequest", method = RequestMethod.GET)
	public String declineBattleRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			remoteService.declineBattleRequest(user);
			userService.setUserStatus(user, UserStatus.ACTIVE);
			session.setAttribute("user", user);
			session.setAttribute("battleId", null);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/remote/gettingStarted", method = RequestMethod.GET)
	public String startGame(Model model, HttpServletRequest request) {
		String pageToLoad = "remotePlaceShips";
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("errorText", "You must log in to play Battleship!");
			pageToLoad = "errorPage";
		} else {
			session.setAttribute("gameMode", "remote");
			model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
			
			user.setHuman(commonService.createHuman());
			userService.setUserStatus(user, UserStatus.OCCUPIED);
			session.setAttribute("user", user);
			
			Battle battle = remoteService.getBattleById((int) session.getAttribute("battleId"));
			battle.setPlayer(user);
		}
		
		return pageToLoad;
	}
}