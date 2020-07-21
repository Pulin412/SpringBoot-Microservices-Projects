package com.kalaha.game.controller;

import com.kalaha.game.entity.Game;
import com.kalaha.game.exception.GameNotFoundException;
import com.kalaha.game.exception.InvalidMoveException;
import com.kalaha.game.service.KalahaService;
import com.kalaha.game.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KalahaController {

    private final KalahaService kalahaService;

    @Value("${kalaha.noOfStones}")
    private Integer noOfStones;

    public KalahaController(KalahaService kalahaService) {
        this.kalahaService = kalahaService;
    }

    @GetMapping("/start")
    public String startNewGame(Model model) {
        Game newGame = kalahaService.startNewGame(noOfStones);
        model.addAttribute("game", newGame);
        model.addAttribute("message", "Player-1 turn");
        return "home";
    }

    @GetMapping("/reset")
    public String resetGame(Model model) {
        Game newGame = kalahaService.startNewGame(noOfStones);
        model.addAttribute("game", newGame);
        model.addAttribute("message", "Player-1 turn");
        return "home";
    }

    @PostMapping("/makeMove")
    public String makeMove(@RequestParam("gameId") Integer gameId, @RequestParam("selectedPit") Integer selectedPit, Model model) throws InvalidMoveException, GameNotFoundException {
        Game game = kalahaService.makeMove(gameId, selectedPit, noOfStones);
        if (game.getStatus() == 1) {
            model.addAttribute("message", game.getPitMap().get(7).getStones() > game.getPitMap().get(14).getStones() ? "Player 1 has won!!" : "Player 2 has won!!");
        }else if(game.getStatus() == 0){
            model.addAttribute("message", game.getNextMove().equals(Constants.PLAYER_ONE) ? "Player-1 turn" : "Player-2 turn");
        }
        model.addAttribute("game", game);
        return "home";
    }
}
