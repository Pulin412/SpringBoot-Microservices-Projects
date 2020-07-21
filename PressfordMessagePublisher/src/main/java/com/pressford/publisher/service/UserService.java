package com.pressford.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pressford.publisher.entities.User;
import com.pressford.publisher.repositories.UserRepository;

/**
 * @author Pulin
 *
 *         Service class for User acting as the middle layer between the
 *         controller and the Repository.
 *
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * @param likedByEmployee
	 * @return User
	 *
	 *         Method to find the User by the user name of the logged in User.
	 */
	public User findByUserId(String likedByEmployee) {
		return userRepository.findByUserId(likedByEmployee);
	}
}
