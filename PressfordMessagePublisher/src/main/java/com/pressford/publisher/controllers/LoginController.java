package com.pressford.publisher.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Pulin
 *
 *         Login Controller for login and access control purpose
 */
@Controller
public class LoginController {

	private static final Logger log = LogManager.getLogger(LoginController.class);

	/**
	 * Request Mapping for login page
	 *
	 * @return Login view
	 */
	@GetMapping("/signin")
	public String login() {

		log.info("LOGGING IN");
		return "login";
	}

	/**
	 * Request Mapping for Access denied page
	 *
	 * @return Access-denied view
	 */
	@GetMapping("/access-denied")
	public String accessDenied() {

		return "/error/access-denied";
	}
}
