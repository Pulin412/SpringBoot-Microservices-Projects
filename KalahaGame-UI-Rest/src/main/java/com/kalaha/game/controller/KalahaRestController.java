package com.kalaha.game.controller;

import com.kalaha.game.entity.Game;
import com.kalaha.game.exception.GameNotFoundException;
import com.kalaha.game.exception.InvalidMoveException;
import com.kalaha.game.service.KalahaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KalahaRestController {

    private final KalahaService kalahaService;

    @Value("${kalaha.noOfStones}")
    private Integer noOfStones;

    public KalahaRestController(KalahaService kalahaService) {
        this.kalahaService = kalahaService;
    }

    @GetMapping("/")
    public ResponseEntity<Game> startNewGame() {
        Game newGame = kalahaService.startNewGame(noOfStones);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @PostMapping("/makeMove/gameId/{gameId}/pitId/{pitId}")
    public ResponseEntity<?> makeMove(@PathVariable("gameId") Integer gameId, @PathVariable("pitId") Integer pitId)
            throws InvalidMoveException, GameNotFoundException {

        Game game = kalahaService.makeMove(gameId, pitId, noOfStones);
        if (game.getStatus() == 1) {
            return new ResponseEntity<String>(game.getPitMap().get(7).getStones() > game.getPitMap().get(14).getStones() ? "Player 1 has won!!" : "Player 2 has won!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
}
