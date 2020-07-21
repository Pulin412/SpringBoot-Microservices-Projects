package com.pressford.publisher.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pressford.publisher.entities.Message;
import com.pressford.publisher.repositories.MessageRepository;

/**
 * @author Pulin
 *
 *         Service class for Message acting as the middle layer between the
 *         controller and the Repository.
 *
 */
@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	/**
	 * @return Messages List
	 *
	 *         Method to find all the saved Messages.
	 */
	public Iterable<Message> findAll() {
		return messageRepository.findAll();
	}

	/**
	 * @param message
	 * @return Message
	 *
	 *         Method to save the Message in the database.
	 */
	public Message save(Message message) {
		return messageRepository.save(message);
	}

	/**
	 * @param id
	 * @return Message
	 *
	 *         Method to find the Message by a particular message id.
	 */
	public Optional<Message> findById(long id) {
		return messageRepository.findById(id);
	}

	/**
	 * @param message
	 *
	 *            Method to delete a Message.
	 */
	public void delete(Message message) {
		messageRepository.delete(message);
	}

}
