package com.pressford.publisher.controllers;

import java.util.Collections;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pressford.publisher.entities.Message;
import com.pressford.publisher.entities.User;
import com.pressford.publisher.service.MessageService;
import com.pressford.publisher.service.UserService;
import com.pressford.publisher.utility.Utility;

/**
 * @author Pulin
 *
 *         Controller for Message related operations like add a message, view a
 *         message, upadate a message, delete a message. Employees can comment
 *         and like the messages. Mapping for contact us and about us pages.
 *
 */
@Controller
public class MessageController {

	private static final Logger log = LogManager.getLogger(MessageController.class);

	@Value("${pressford.errors.likeExhausted}")
	private String likeExhaustedMessage;

	@Value("${pressford.errors.liketwice}")
	private String likeTwiceMessage;

	public MessageController(final MessageService messageService, final UserService userService) {

		this.messageService = messageService;
		this.userService = userService;
	}

	/**
	 * Autowired User and Message Services.
	 */
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	@GetMapping("/graph")
	public String graph() {

		MessageController.log.info("INSIDE graph METHOD OF MESSAGE CONTROLLER");
		return "display";
	}

	/**
	 * @param model
	 * @return Index view
	 *
	 *         Request Mapping for the home page after successful authentication.
	 */
	@GetMapping("/home")
	public String home(Model model) {

		MessageController.log.info("INSIDE home METHOD OF MESSAGE CONTROLLER");
		String loggedInUser = Utility.getUserFromContext();
		User user = this.userService.findByUserId(loggedInUser);
		MessageController.log.debug("FETCHED LOGGED IN USERS");
		if (user != null) {
			model.addAttribute("availableLikes", user.getRemainingLikes());
		} else {
			model.addAttribute("availableLikes", 0);
		}
		model = Utility.setModelBasedOnRole(model);
		model.addAttribute("messages", this.messageService.findAll());
		model.addAttribute("user", loggedInUser);
		return "index";
	}

	/**
	 * @param message
	 * @return
	 */
	@GetMapping("/signup")
	public String addMessage(final Message message) {

		MessageController.log.info("INSIDE addMessage GET METHOD OF MESSAGE CONTROLLER");
		return "add-message";
	}

	/**
	 * @param message
	 * @param result
	 * @param model
	 * @return Index view or Add message view according to the errors
	 *
	 *         Request Mapping for publishing a new message by the Publisher.
	 */
	@PostMapping("/addmessage")
	public String addMessage(@Valid final Message message, final BindingResult result, Model model) {

		// checking for any errors coming from the thymeleaf page. If there are
		// any,
		// return to the same page with error details.
		MessageController.log.info("INSIDE addMessage POST METHOD OF MESSAGE CONTROLLER");
		if (result.hasErrors()) {
			return "add-message";
		}
		message.setLikedByEmployees(Collections.emptySet());
		MessageController.log.debug("INSIDE addMessage POST METHOD OF MESSAGE CONTROLLER : SAVING MESSAGE");
		this.messageService.save(message);
		MessageController.log.debug("INSIDE addMessage POST METHOD OF MESSAGE CONTROLLER : MESSAGE SAVED");
		model = Utility.setModelBasedOnRole(model);
		model.addAttribute("messages", this.messageService.findAll());
		model.addAttribute("user", Utility.getUserFromContext());
		return "index";
	}

	/**
	 * @param id
	 * @param model
	 * @return update message view
	 *
	 *         Request Mapping to show the update page on click of update icon.
	 */
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") final long id, final Model model) {

		// check if the message selected is having the valid id or not.
		MessageController.log.info("INSIDE showUpdateForm GET METHOD OF MESSAGE CONTROLLER");
		Message message = this.messageService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
		model.addAttribute("message", message);
		return "update-message";
	}

	/**
	 * @param id
	 * @param message
	 * @param result
	 * @param model
	 * @return Index view or the update message view according to the errors
	 *
	 *         Request Mapping to update the message.
	 */
	@PostMapping("/update/{id}")
	public String updateMessage(@PathVariable("id") final long id, @Valid final Message message,
			final BindingResult result, Model model) {

		/*
		 * checking for any errors coming from the thymeleaf page. If there are any,
		 * return to the same page with error details
		 */
		MessageController.log.info("INSIDE updateMessage POST METHOD OF MESSAGE CONTROLLER");
		if (result.hasErrors()) {
			message.setId(id);
			return "update-message";
		}
		MessageController.log.debug("INSIDE updateMessage POST METHOD OF MESSAGE CONTROLLER : SAVING MESSAGE");
		this.messageService.save(message);
		MessageController.log.debug("INSIDE updateMessage POST METHOD OF MESSAGE CONTROLLER : MESSAGE SAVED");
		model.addAttribute("messages", this.messageService.findAll());
		model = Utility.setModelBasedOnRole(model);
		model.addAttribute("user", Utility.getUserFromContext());
		return "index";
	}

	/**
	 * @param id
	 * @param model
	 * @return Index view
	 *
	 *         Request Mapping to delete the selected message
	 */
	@GetMapping("/delete/{id}")
	public String deleteMessage(@PathVariable("id") final long id, Model model) {

		// check if the message selected is having the valid id or not.
		MessageController.log.info("INSIDE deleteMessage METHOD OF MESSAGE CONTROLLER");
		Message message = this.messageService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));

		MessageController.log.debug("INSIDE deleteMessage POST METHOD OF MESSAGE CONTROLLER : DELETING MESSAGE");
		this.messageService.delete(message);
		MessageController.log.debug("INSIDE deleteMessage POST METHOD OF MESSAGE CONTROLLER : MESSAGE DELETED");
		model.addAttribute("messages", this.messageService.findAll());
		model = Utility.setModelBasedOnRole(model);
		model.addAttribute("user", Utility.getUserFromContext());
		return "index";
	}

	/**
	 * @param id
	 * @param model
	 * @return Index view
	 *
	 *         Request Mapping to view any message and returning with error messages
	 *         (if any) on the same view.
	 */
	@GetMapping("/like/{id}")
	public String likeMessage(@PathVariable("id") final long id, Model model) {

		// check if the message selected is having the valid id or not.
		MessageController.log.info("INSIDE likeMessage METHOD OF MESSAGE CONTROLLER");
		Message message = this.messageService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));

		int userLikes = 0;
		Set<User> likedByEmployees = null;

		String likedByEmployee = Utility.getUserFromContext();
		User loggedInUser = this.userService.findByUserId(likedByEmployee);
		int maxNoOfLikes = loggedInUser.getNoOfLikes();
		userLikes = loggedInUser.getLikesUsed();

		/*
		 * 1. Fetched the number of likes used by the user and the max that can be used.
		 *
		 * 2. If user has exhausted the number of likes, returning with error message
		 * that you have exhausted the number of likes.
		 *
		 * 3. If user has likes left to be used, checking first if the user has already
		 * liked the message, if yes, returning with error message that user can't like
		 * the post again.
		 *
		 * 4. Setting one model attribute for remaining likes to show on the view.
		 *
		 * 5. Returning to the view after the saving the message.
		 */
		MessageController.log.info("CHECKING USERLIKES AND MAX NUMBER OF LIKES");
		if (userLikes < maxNoOfLikes) {
			likedByEmployees = null;
			likedByEmployees = message.getLikedByEmployees();
			if (likedByEmployees != null && !likedByEmployees.contains(loggedInUser)) {
				MessageController.log.info("INSIDE IF BLOCK AFTER VERIFING LIKES OF MESSAGE CONTROLLER");
				loggedInUser = Utility.adjustLikes(loggedInUser);
				message.getLikedByEmployees().add(loggedInUser);
				loggedInUser.getMessages().add(message);
				MessageController.log.debug("INSIDE likeMessage POST METHOD OF MESSAGE CONTROLLER : SAVING MESSAGE");
				this.messageService.save(message);
				MessageController.log.debug("INSIDE likeMessage POST METHOD OF MESSAGE CONTROLLER : MESSAGE SAVED");
			} else {
				MessageController.log.error("INSIDE ELSE BLOCK IF SAME POST IS LIKED AGAIN OF MESSAGE CONTROLLER");
				model.addAttribute("messages", this.messageService.findAll());
				model.addAttribute("likeerror", this.likeTwiceMessage);
				model = Utility.setModelBasedOnRole(model);
				model.addAttribute("availableLikes", loggedInUser.getRemainingLikes());
				model.addAttribute("user", loggedInUser.getUserId());
				return "index";
			}
		} else {
			MessageController.log.error("INSIDE ELSE BLOCK IF LIKES ARE EXHAUSTED OF MESSAGE CONTROLLER");
			model.addAttribute("messages", this.messageService.findAll());
			model.addAttribute("likeerror", this.likeExhaustedMessage);
			model = Utility.setModelBasedOnRole(model);
			model.addAttribute("availableLikes", 0);
			model.addAttribute("user", loggedInUser.getUserId());
			return "index";
		}

		model.addAttribute("availableLikes", loggedInUser.getRemainingLikes());
		model.addAttribute("messages", this.messageService.findAll());
		model = Utility.setModelBasedOnRole(model);
		model.addAttribute("user", loggedInUser.getUserId());
		return "index";
	}

	/**
	 * @return Contact us view
	 *
	 *         Request Mapping to show the contact us view.
	 */
	@GetMapping("/contact")
	public String contact() {

		MessageController.log.info("INSIDE contact METHOD OF MESSAGE CONTROLLER");
		return "contact";
	}

	/**
	 * @return About us view
	 *
	 *         Request Mapping to show the about us view.
	 */
	@GetMapping("/about")
	public String about() {

		MessageController.log.info("INSIDE about METHOD OF MESSAGE CONTROLLER");
		return "about";
	}

}
