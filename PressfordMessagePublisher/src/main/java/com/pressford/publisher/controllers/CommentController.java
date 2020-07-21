package com.pressford.publisher.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pressford.publisher.entities.Comment;
import com.pressford.publisher.entities.Message;
import com.pressford.publisher.entities.User;
import com.pressford.publisher.service.CommentService;
import com.pressford.publisher.service.MessageService;
import com.pressford.publisher.service.UserService;
import com.pressford.publisher.utility.Utility;

/**
 * @author Pulin
 *
 *         Controller for Comment related operations
 */
@Controller
public class CommentController {

	private static final Logger log = LogManager.getLogger(MessageController.class);

	@Autowired
	private CommentService commentService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	/**
	 * @param messageId
	 * @return
	 */
	@GetMapping("/messages/{messageId}/getcomments")
	public List<Comment> getAllCommentsByMessageId(@PathVariable(value = "messageId") Long messageId) {
		log.info("INSIDE getAllCommentsByMessageId METHOD OF COMMENT CONTROLLER");
		return commentService.findByMessageId(messageId);
	}

	/**
	 * @param model
	 * @param id
	 * @return Add comment View
	 *
	 *         Get mapping to render the add comment page with the exisiting
	 *         comments for the selected message
	 */
	@RequestMapping(value = "/comment/save/{id}")
	public String addComment(ModelMap model, @PathVariable("id") long id) {
		log.info("INSIDE addComment METHOD OF COMMENT CONTROLLER");
		Message message = messageService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
		List<Comment> comments = getAllCommentsByMessageId(id);

		model.addAttribute("newComment", new Comment());
		model.addAttribute("messages", message);
		model.addAttribute("id", id);
		if (!comments.isEmpty()) {
			model.addAttribute("exisitingComments", comments);
		} else {
			model.addAttribute("exisitingComments", null);
		}
		return "add-comment";
	}

	/**
	 * @param newComment
	 * @param result
	 * @param model
	 * @param id
	 * @return add comment view in case of error or index page after successful
	 *         addition
	 *
	 *         Post method to add the comment for the particular message
	 */
	@RequestMapping(value = "/comment/save/{id}", method = RequestMethod.POST)
	public String saveComment(@Valid @ModelAttribute("newComment") Comment newComment, BindingResult result,
			Model model, @PathVariable("id") long id) {

		/*
		 * 1. Fetched the message first on the basis of the selected message id.
		 *
		 * 2. Fetched the logged in user
		 *
		 * 3. Fetched all the existing comments in case of any error and to return to
		 * the same page.
		 *
		 * 3. Save the comment.
		 *
		 * 4. Return the view.
		 */
		log.info("INSIDE saveComment METHOD OF COMMENT CONTROLLER");
		Message message = messageService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
		String loggedInUserName = Utility.getUserFromContext();
		if (result.hasErrors()) {
			model.addAttribute("messages", message);
			model.addAttribute("id", id);
			List<Comment> comments = getAllCommentsByMessageId(id);
			model.addAttribute("exisitingComments", comments);
			return "add-comment";
		} else {
			newComment.setMessage(message);
			newComment.setUserName(loggedInUserName);
			log.debug("INSIDE getAllCommentsByMessageId METHOD OF COMMENT CONTROLLER : SAVING COMMENT");
			commentService.save(newComment);
			log.debug("INSIDE getAllCommentsByMessageId METHOD OF COMMENT CONTROLLER : COMMENT SAVED");
		}
		model.addAttribute("messages", messageService.findAll());
		model.addAttribute("comments", commentService.findAll());
		model = Utility.setModelBasedOnRole(model);
		User user = userService.findByUserId(loggedInUserName);

		if (user != null) {
			model.addAttribute("availableLikes", user.getRemainingLikes());
		} else {
			model.addAttribute("availableLikes", 0);
		}
		model.addAttribute("user", loggedInUserName);
		return "index";
	}
}
