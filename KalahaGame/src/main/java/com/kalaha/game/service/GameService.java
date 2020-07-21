/**
 * 
 */
package com.kalaha.game.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalaha.game.dao.GameDao;
import com.kalaha.game.model.Game;

/**
 * @author Pulin
 *
 */
@Service
public class GameService {

	@Autowired
	GameDao gameDao;

	public Game createUpdateGame(Game game) {
		return gameDao.save(game);
	}

	public Optional<Game> findById(Long id) {
		return gameDao.findById(id);
	}
}
