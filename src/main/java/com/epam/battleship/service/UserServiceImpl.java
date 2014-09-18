package com.epam.battleship.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.battleship.domain.User;
import com.epam.battleship.domain.UserStatus;
import com.epam.battleship.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void setUserStatus(User user, UserStatus status) {
		user.setStatus(status.name());
		user.setLastLoginDate(new Date());
		userRepository.save(user);
	}

	@Override
	public void refreshUserLogIn(User user) {
//		user.setStatus(UserStatus.ACTIVE.name());
		user.setLastLoginDate(new Date());
		userRepository.save(user);
	}

	@Override
	public void cleanExpiredLogIns(Date date) {
		List<User> userList = userRepository.findAll();
		for(User user : userList){
			if (user.getLastLogInDate().compareTo(date) == -1) {
				user.setStatus(UserStatus.INACTIVE.name());
			}
		}
		userRepository.save(userList);
	}
	
	@Override
	public List<User> getOpponents(User user) {
		return userRepository.getOpponents(UserStatus.ACTIVE.name(), user.getId());
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findOne(id);
	}
}
