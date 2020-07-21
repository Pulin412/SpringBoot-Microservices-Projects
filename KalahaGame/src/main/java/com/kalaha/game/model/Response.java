/**
 * 
 */
package com.kalaha.game.model;

import java.util.Map;

/**
 * @author Pulin
 *	Model for success response
 */
public class Response {

	private Long id;
	private String url;
	private Map<Integer, Integer> status;

	public Response(String url, Long gameId, Map<Integer, Integer> generateResponseMap) {
		this.id = gameId;
		this.status = generateResponseMap;
		this.url = url;
	}

	/**
	 * @return the gameId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param gameId the gameId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the statusMap
	 */
	public Map<Integer, Integer> getStatus() {
		return status;
	}

	/**
	 * @param statusMap the statusMap to set
	 */
	public void setStatus(Map<Integer, Integer> status) {
		this.status = status;
	}

}
