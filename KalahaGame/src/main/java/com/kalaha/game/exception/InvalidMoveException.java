/**
 * 
 */
package com.kalaha.game.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Pulin
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidMoveException extends Exception {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String url;
	private Map<Integer, Integer> status;
	private String message;

	/**
	 * @param game
	 * @param url
	 * @param errors
	 */
	public InvalidMoveException(String message, String url, Map<Integer, Integer> status, Long id) {
		super();
		this.message = message;
		this.status = status;
		this.id = id;
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	public Long getId() {
		return id;
	}

	public Map<Integer, Integer> getStatus() {
		return status;
	}

	/**
	 * @return the errorMessage
	 */
	public String getMessage() {
		return message;
	}
}
