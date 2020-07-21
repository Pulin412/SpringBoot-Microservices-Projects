package com.kalaha.game.exception;

public class GameNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final Long id;
	private String message;
	private String url;

	/**
	 * @param id
	 * @param message
	 * @param url
	 * @param status
	 */
	public GameNotFoundException(Long id, String message, String url) {
		super();
		this.id = id;
		this.message = message;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}
}
