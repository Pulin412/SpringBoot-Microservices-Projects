/**
 * 
 */
package com.kalaha.game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalaha.game.exception.GameNotFoundException;
import com.kalaha.game.exception.InvalidMoveException;
import com.kalaha.game.model.Game;
import com.kalaha.game.model.Response;
import com.kalaha.game.service.GameService;
import com.kalaha.game.util.Constants;
import com.kalaha.game.util.GameUtility;

/**
 * @author Pulin
 *
 *         Rest Controller with 2 end points - 1. Create a new Game. 2. Make a
 *         move.
 */
@RestController
public class GameController {

	private GameService service;

	/**
	 * 
	 */
	@Autowired
	public GameController(GameService service) {
		this.service = service;
	}

	/**
	 * Configurable number of stones. Can be configured in the
	 * application.properties file
	 */
	@Value("${com.kalaha.game.noOfStones}")
	private int noOfStones;

	private boolean retainChance = false;
	private String winner;
	private boolean gameEnded = false;

	/**
	 * @param request
	 * @return Response entity with a new created game.
	 * 
	 *         Creates a new Game
	 */
	@PostMapping("/games")
	public ResponseEntity<?> createGame(HttpServletRequest request) {

		Map<String, String> returnMap = new HashMap<String, String>();

		/*
		 * Create a new game
		 */
		Long newGameId = createNewGame();

		/*
		 * Return the response with - 1. Newly created game Id. 2. URI of the created
		 * game. 3. HTTP Response status as Created.
		 */
		returnMap.put("id", String.valueOf(newGameId));
		returnMap.put("uri", request.getRequestURL().toString() + "/" + newGameId);
		return new ResponseEntity<>(returnMap, HttpStatus.CREATED);
	}

	/**
	 * @param request
	 * @param gameId
	 * @param pitId
	 * @return ResponseEntity
	 * @throws GameNotFoundException
	 * @throws InvalidMoveException
	 * 
	 *                               Makes a move for the passed gameId and pitId
	 */
	@PutMapping("/games/{gameId}/pits/{pitId}")
	public ResponseEntity<Object> makeMove(HttpServletRequest request, @PathVariable(value = "gameId") Long gameId,
			@PathVariable(value = "pitId") Integer pitId) throws GameNotFoundException, InvalidMoveException {

		/*
		 * Fetch the hostname for the player who played the current move
		 */
		String playerHost = request.getRemoteHost();
		/*
		 * Fetch the URL from the HTTP request.
		 */
		String url = request.getRequestURL().toString();

		/*
		 * Fetch the existing game with the passed GameId
		 */
		Optional<Game> runningGame = service.findById(gameId);
		Game game = null;

		/*
		 * If Game is not present, throw a Game not found exception
		 */
		if (!runningGame.isPresent()) {
			throw new GameNotFoundException(gameId, Constants.GAME_NOT_FOUND, url);
		}

		game = runningGame.get();
		/*
		 * If the game is already ended, throw a new exception with the proper message
		 */
		if (this.isGameEnded()) {
			throw new InvalidMoveException(Constants.GAME_ENDED, url, GameUtility.generateResponseMap(game), gameId);
		}

		/*
		 * If its the first move, save the Player one host name in the game.
		 */
		if (game.getPlayerOneHostName() == null) {
			/*
			 * If player one selects pit from the other player's pits, throw an exception
			 */
			if (pitId > 6) {
				throw new InvalidMoveException(Constants.PLAYERONE_INVALID_PITID, url, GameUtility.generateResponseMap(game),
						gameId);
			}
			game.setPlayerOneHostName(playerHost);
		}

		/*
		 * If its the second move and the playing turn is not retained by player one,
		 * save the Player two host name in the game.
		 */
		else if (game.getPlayerTwoHostName() == null && !this.isRetainChance()) {
			/*
			 * If player two selects pit from the other player's pits, throw an exception
			 */
			if (pitId < 8) {
				throw new InvalidMoveException(Constants.PLAYERTWO_INVALID_PITID, url, GameUtility.generateResponseMap(game),
						gameId);
			}
			game.setPlayerTwoHostName(playerHost);
		}

		/*
		 * validate the move before computing the move.
		 */
		validateMove(game, playerHost, pitId, url);

		/*
		 * If the stones in the pit are 0, throw an exception
		 */
		if (game.getPits().get(pitId - 1) == 0) {
			throw new InvalidMoveException(Constants.ZERO_STONES, url, GameUtility.generateResponseMap(game), game.getId());
		} else {
			/*
			 * Make a move if everything validation is fine.
			 */
			game = computeMove(game, pitId, playerHost);

			/*
			 * Check if the game has ended after this move and set the pits for each player.
			 */
			if (checkForWin(game, playerHost)) {
				if (this.getWinner().equalsIgnoreCase(game.getPlayerOneHostName()))
					game.setPits(GameUtility.populateWinnerKalahaPits(Constants.PLAYER_ONE, game));
				if (this.getWinner().equalsIgnoreCase(game.getPlayerTwoHostName()))
					game.setPits(GameUtility.populateWinnerKalahaPits(Constants.PLAYER_TWO, game));
				this.setGameEnded(true);
			}
		}
		/*
		 * set the LastMoveBy with the remote host name before saving the game.
		 */
		game.setLastMoveBy(playerHost);

		/*
		 * persist the game.
		 */
		service.createUpdateGame(game);

		/*
		 * Return with the Response model and HTTP Status as OK
		 */
		return new ResponseEntity<Object>(new Response(url, gameId, GameUtility.generateResponseMap(game)), HttpStatus.OK);
	}

	/**
	 * @param game
	 * @param playerHost
	 * @param pitId
	 * @param url
	 * 
	 *                   Checks if the move made in by the correct player or not.
	 *                   Returns false in case of incorrect player making the move.
	 * @throws InvalidMoveException
	 */
	private void validateMove(Game game, String playerHost, Integer pitId, String url) throws InvalidMoveException {

		/*
		 * Check if the chance is not retained then current move should be made by the
		 * next player.
		 */
		if (!this.isRetainChance() && playerHost.equalsIgnoreCase(game.getLastMoveBy())) {
			throw new InvalidMoveException(Constants.NEXT_PLAYER_CHANCE, url, GameUtility.generateResponseMap(game), game.getId());
		}
		/*
		 * Check if the chance is retained then current move should be made by the same
		 * player.
		 */
		else if (this.isRetainChance() && !playerHost.equalsIgnoreCase(game.getLastMoveBy())) {
			throw new InvalidMoveException(Constants.CHANCE_RETAINED, url, GameUtility.generateResponseMap(game), game.getId());
		}
		/*
		 * Check if the pitID are valid or not, valid values are between 1-5 or 7-13
		 */
		else if (pitId == 7 || pitId <= 0 || pitId > 13) {
			throw new InvalidMoveException(Constants.INVALID_PIT_GIVEN, url, GameUtility.generateResponseMap(game), game.getId());
		}
		/*
		 * Check if the chance is of player one, pitId should be between 1-5 and if the
		 * chance is of player two, pitId should be between 7-13
		 */
		else if ((playerHost.equalsIgnoreCase(game.getPlayerOneHostName()) && pitId > 6) 
				|| (playerHost.equalsIgnoreCase(game.getPlayerTwoHostName()) && pitId < 6)) {
			throw new InvalidMoveException(Constants.NOT_PLAYER_PIT, url, GameUtility.generateResponseMap(game), game.getId());
		}
	}
	
	/**
	 * @param game
	 * @param playerHost
	 * @return boolean
	 * 
	 *         check if the game has already ended and return boolean value
	 */
	private boolean checkForWin(Game game, String playerHost) {

		List<Integer> pits = game.getPits();
		int playerOneStones = 0;
		int playerTwoStones = 0;
		int index = 0;

		/*
		 * calculate the total stones in player One pits with index as 0-5
		 */
		while (index < 6) {
			playerOneStones = playerOneStones + pits.get(index);
			index++;
		}

		index++;

		/*
		 * calculate the total stones in player Two pits with index as 7-12
		 */
		while (index < 13) {
			playerTwoStones = playerTwoStones + pits.get(index);
			index++;
		}

		/*
		 * check if any player's total stones are 0, return true and set the winner
		 * name, else return false
		 */
		if (playerOneStones == 0) {
			/*
			 * add all the player two's stones to the Kalaha of Player two if total stones
			 * in pits are 0
			 */
			pits.set(13, pits.get(13) + playerTwoStones);
			if (pits.get(6) > pits.get(13)) {
				this.setWinner(game.getPlayerOneHostName());
			} else {
				this.setWinner(game.getPlayerTwoHostName());
			}
			return true;
		} else if (playerTwoStones == 0) {
			/*
			 * add all the player one's stones to the Kalaha of Player one if total stones
			 * in pits are 0
			 */
			pits.set(6, pits.get(6) + playerOneStones);
			if (pits.get(6) > pits.get(13)) {
				this.setWinner(game.getPlayerOneHostName());
			} else {
				this.setWinner(game.getPlayerTwoHostName());
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param game
	 * @param pitId
	 * @param playerHost
	 * @return updated Game
	 * 
	 *         Method to make the move by updating all the pits values
	 */
	private Game computeMove(Game game, Integer pitId, String playerHost) {

		int index = pitId; // index for pits list
		int stones = 0;
		int skipHome = 0; // which kalaha to be skipped while making a move by any player
		int pitIndex = 0;
		int opppositePitIndex = 0; // opposite pit index if stones to added from the opposite pit as well
		int kalahaIndex = 0; // kalaha index to save the stones for current pit and opposite pit
		boolean ownPit = false;

		List<Integer> pits = game.getPits();

		/*
		 * check if the selected pit is of player one or two and skip the kalaha
		 * accordingly
		 */
		if (index < 7) {
			skipHome = 13;
		} else {
			skipHome = 6;
		}

		/*
		 * get the stones in the pit selected
		 */
		if (pits.size() > 0) {
			stones = pits.get(index - 1);
		}

		/*
		 * mark the kalaha index as per the player who is making the move
		 */
		if (playerHost.equalsIgnoreCase(game.getPlayerOneHostName())) {
			kalahaIndex = 6;
		} else {
			kalahaIndex = 13;
		}

		/*
		 * set the stones as 0 for the selected pit
		 */
		pits.set(index - 1, 0);

		/*
		 * loop through the rest of the pits unless number of stones are greater than 0
		 */
		while (stones > 0) {
			/*
			 * get the pit index where the stones are to be added
			 */
			pitIndex = (index) % 14;
			if (pitIndex != skipHome) {
				/*
				 * check in which kalaha to place the stones if the last stone lands in an own
				 * empty pit
				 */
				if (kalahaIndex == 6 && pitIndex < 6) {
					ownPit = true;
				} else if (kalahaIndex == 13 && pitIndex > 7) {
					ownPit = true;
				}
				/*
				 * If the last stone is left and the pit has 0 stones - check if the pit is not
				 * any of the kalaha and stone to be placed is in own pit -
				 * 
				 * 1. Get the opposite pit index. 2. Set the stones in the kalaha with all the
				 * stones from own pit and opposite pit 3. Set the pits in the own pit and
				 * opposite pit as 0
				 */
				if (stones == 1 && pits.get(pitIndex) == 0 && !(pitIndex == 6 || pitIndex == 13) && ownPit) {
					opppositePitIndex = pits.size() - 2 - pitIndex;
					pits.set(kalahaIndex, 1 + pits.get(opppositePitIndex) + pits.get(kalahaIndex));
					pits.set(pitIndex, 0);
					pits.set(opppositePitIndex, 0);
				} else {
					pits.set(pitIndex, pits.get(pitIndex) + 1);
				}
				stones--;
			}
			index++;
			ownPit = false;
		}

		/*
		 * If the players last stone lands in his own Kalaha, he gets another turn. Set
		 * the retainChance flag as true.
		 */
		if ((pitIndex == 6 && playerHost.equalsIgnoreCase(game.getPlayerOneHostName()))
				|| (pitIndex == 13 && playerHost.equalsIgnoreCase(game.getPlayerTwoHostName()))) {
			this.setRetainChance(true);
		} else {
			this.setRetainChance(false);
		}

		return game;
	}

	/**
	 * @return Long gameId
	 * 
	 *         Creates a new game.
	 */
	private Long createNewGame() {

		Game newGame = new Game();
		newGame.setPits(GameUtility.populatePlayersMap(noOfStones));
		service.createUpdateGame(newGame);
		return newGame.getId();
	}

	/**
	 * @return the retainChance
	 */
	public boolean isRetainChance() {
		return retainChance;
	}

	/**
	 * @param retainChance the retainChance to set
	 */
	public void setRetainChance(boolean retainChance) {
		this.retainChance = retainChance;
	}

	/**
	 * @return the winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}

	/**
	 * @return the gameEnded
	 */
	public boolean isGameEnded() {
		return gameEnded;
	}

	/**
	 * @param gameEnded the gameEnded to set
	 */
	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

}
