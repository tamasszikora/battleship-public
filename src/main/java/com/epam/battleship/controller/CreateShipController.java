package com.epam.battleship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.epam.battleship.domain.Human;
import com.epam.battleship.domain.Ship;
import com.epam.battleship.domain.User;
import com.epam.battleship.service.RemoteBattleshipService;

@Controller
@SessionAttributes("shipForm")
public class CreateShipController {

	@Autowired
	private RemoteBattleshipService battleshipService;
	
	@RequestMapping(value = "/createShip", method = RequestMethod.GET, params = "step=addShipPart")
	public @ResponseBody String addShipPart(final ShipForm shipForm, final Integer position) {
		int result = 0;
		if (position != null) {
			result = shipForm.changeShapeMatrix(position);
		}
		return String.valueOf(result);
	}

	@RequestMapping(value = "/createShip", method = RequestMethod.POST, params = "step=newShip")
	public String addCustomShip(ShipForm shipForm, final SessionStatus sessionStatus, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		
		if (shipForm.getName() != null && shipForm.getSize() > 0) {
			Ship ship = new Ship(shipForm.getName(), shipForm.getSize(), shipForm.getShapeMatrix());
			User user = (User) session.getAttribute("user");
			Human human = user.getHuman();
			human.addPlaceableShip(ship);
			session.setAttribute("user", user);
			shipForm.resetShipForm();
		}
		
		return "createShip";
	}

	@RequestMapping(value = "/createShip", method = RequestMethod.POST, params = "step=done")
	public String addCustomShipDone(ShipForm shipForm, final SessionStatus sessionStatus, final Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("threeDimension", session.getAttribute("threeDimension").toString());
		shipForm.resetShipForm();
		
		User user = (User) session.getAttribute("user");
		Human human = user.getHuman();
		model.addAttribute("placeableShips", human.getPlaceableShips());
		
		return "localPlaceShips";
	}
}