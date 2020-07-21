/**
 * 
 */
package com.kalaha.game.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Pulin
 */
@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ElementCollection
	private List<Integer> pits;
	private String playerOneHostName;
	private String playerTwoHostName;
	private String lastMoveBy;

	/**
	 * 
	 */
	public Game() {
		super();
	}

	/**
	 * @param id
	 * @param pits
	 */
	public Game(List<Integer> pits, String playerOneHostName) {
		super();
		this.pits = pits;
		this.playerOneHostName = playerOneHostName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the playerOneHostName
	 */
	public String getPlayerOneHostName() {
		return playerOneHostName;
	}

	/**
	 * @param playerOneHostName the playerOneHostName to set
	 */
	public void setPlayerOneHostName(String playerOneHostName) {
		this.playerOneHostName = playerOneHostName;
	}

	/**
	 * @return the playerTwoHostName
	 */
	public String getPlayerTwoHostName() {
		return playerTwoHostName;
	}

	/**
	 * @param playerTwoHostName the playerTwoHostName to set
	 */
	public void setPlayerTwoHostName(String playerTwoHostName) {
		this.playerTwoHostName = playerTwoHostName;
	}

	/**
	 * @return the lastMoveBy
	 */
	public String getLastMoveBy() {
		return lastMoveBy;
	}

	/**
	 * @param lastMoveBy the lastMoveBy to set
	 */
	public void setLastMoveBy(String lastMoveBy) {
		this.lastMoveBy = lastMoveBy;
	}

	/**
	 * @return the pits
	 */
	public List<Integer> getPits() {
		return pits;
	}

	/**
	 * @param pits the pits to set
	 */
	public void setPits(List<Integer> pits) {
		this.pits = pits;
	}
}
