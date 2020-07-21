package com.pressford.publisher.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pressford.publisher.entities.Comment;
import com.pressford.publisher.repositories.CommentRepository;

/**
 * @author Pulin
 *
 *         Service class for Comment acting as the middle layer between the
 *         controller and the Repository.
 */
@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	/**
	 * @param messageId
	 * @return List of Comment
	 *
	 *         Method to fetch the comments on the selected message
	 */
	public List<Comment> findByMessageId(Long messageId) {
		return commentRepository.findByMessageId(messageId);
	}

	/**
	 * @param id
	 * @param messageId
	 * @return Optional comment
	 *
	 *         Method to fetch the comment on the basis of id and message id
	 */
	public Optional<Comment> findByIdAndMessageId(Long id, Long messageId) {
		return commentRepository.findByIdAndMessageId(id, messageId);
	}

	/**
	 * @param comment
	 * @return Comment
	 *
	 *         Save the comment object in the database
	 */
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	/**
	 * @return List of Comment
	 *
	 *         Get all the comments
	 */
	public List<Comment> findAll() {
		return (List<Comment>) commentRepository.findAll();
	}
}
