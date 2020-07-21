package com.kalaha.game.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalaha.game.model.Game;

@Repository
public interface GameDao extends JpaRepository<Game, Long>{

}
