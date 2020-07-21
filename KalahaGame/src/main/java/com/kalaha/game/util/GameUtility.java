/**
 * 
 */
package com.kalaha.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kalaha.game.model.Game;

/**
 * @author Pulin
 *
 *         Utility class
 */
public class GameUtility {

	/**
	 * @param noOfStones
	 * @return pits List
	 * 
	 *         populate the pits as per the number of stones passed
	 */
	public static List<Integer> populatePlayersMap(int noOfStones) {

		List<Integer> status = new ArrayList<Integer>();

		// setting initial pits with 0 stones for player one
		for (int i = 0; i <= 5; i++) {
			status.add(noOfStones);
		}
		// Initial stones in player one's kalaha would be 0
		status.add(0);

		// setting initial pits with 0 stones for player two
		for (int i = 0; i <= 5; i++) {
			status.add(noOfStones);
		}
		// Initial stones in player two's kalaha would be 0
		status.add(0);

		return status;
	}

	/**
	 * @param winner
	 * @param game
	 * @return pits list
	 * 
	 *         populate the pits once the game is finished
	 */
	public static List<Integer> populateWinnerKalahaPits(String winner, Game game) {

		List<Integer> pits = game.getPits();

		/*
		 * set all pits to 0 once all the stones are added to the kalaha for player one
		 */
		for (int i = 0; i <= 5; i++) {
			pits.set(i, 0);
		}

		/*
		 * set all pits to 0 once all the stones are added to the kalaha for player two
		 */
		for (int i = 7; i <= 12; i++) {
			pits.set(i, 0);
		}
		return game.getPits();
	}
	
	/**
	 * @param game
	 * @return Map with pit index and stones
	 * 
	 *         Generate the pits with stones in it as the final response after the
	 *         move.
	 */
	public static Map<Integer, Integer> generateResponseMap(Game game) {

		Map<Integer, Integer> statusMap = new HashMap<Integer, Integer>();
		int index = 1;
		for (int pit : game.getPits()) {
			statusMap.put(index, pit);
			index++;
		}
		return statusMap;
	}

}
