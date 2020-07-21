/**
 * 
 */
package com.kalaha.game.model;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * @author Pulin
 *	Model for return Error Response
 */
public class ErrorResponse {

	private HttpStatus httpStatus;
	private String error;
	private Long id;
	private String url;
	private Map<Integer, Integer> status;

	public ErrorResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<Integer, Integer> getStatus() {
		return status;
	}

	public void setStatus(Map<Integer, Integer> status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
