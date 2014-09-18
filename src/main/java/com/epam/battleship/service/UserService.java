package com.epam.battleship.service;

import java.util.Date;
import java.util.List;

import com.epam.battleship.domain.User;
import com.epam.battleship.domain.UserStatus;

public interface UserService {
	 	 
	 void setUserStatus(User user, UserStatus status);

	 void refreshUserLogIn(User user);
	 
	 void cleanExpiredLogIns(Date date);
	 
	 List<User> getOpponents(User user);
	 
	 List<User> getUsers();
	 
	 User getUser(Long id);
}
