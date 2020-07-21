package com.pressford.publisher.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author Pulin
 *
 *         User entity to store the user related fields.
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String userId;
	private int noOfLikes;
	private int likesUsed;
	private int remainingLikes;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "likedByEmployees")
	private Set<Message> messages = new HashSet<>();

	/**
	 * Default Constructor
	 */
	public User() {
		super();
	}

	/**
	 * @param userId
	 * @param noOfLikes
	 * @param likesUsed
	 * @param remainingLikes
	 *
	 *            Parameterized Constructor
	 */
	public User(String userId, int noOfLikes, int likesUsed, int remainingLikes) {
		super();
		this.userId = userId;
		this.noOfLikes = noOfLikes;
		this.likesUsed = likesUsed;
		this.remainingLikes = remainingLikes;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the noOfLikes
	 */
	public int getNoOfLikes() {
		return noOfLikes;
	}

	/**
	 * @param noOfLikes
	 *            the noOfLikes to set
	 */
	public void setNoOfLikes(int noOfLikes) {
		this.noOfLikes = noOfLikes;
	}

	/**
	 * @return the likesUsed
	 */
	public int getLikesUsed() {
		return likesUsed;
	}

	/**
	 * @param likesUsed
	 *            the likesUsed to set
	 */
	public void setLikesUsed(int likesUsed) {
		this.likesUsed = likesUsed;
	}

	/**
	 * @return the remainingLikes
	 */
	public int getRemainingLikes() {
		return remainingLikes;
	}

	/**
	 * @param remainingLikes
	 *            the remainingLikes to set
	 */
	public void setRemainingLikes(int remainingLikes) {
		this.remainingLikes = remainingLikes;
	}

	// public void adjustLikes() {
	// remainingLikes--;
	// likesUsed++;
	// }

	/**
	 * @return the messages
	 */
	public Set<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
