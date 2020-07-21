package com.kalaha.game.service;

import com.kalaha.game.entity.Game;
import com.kalaha.game.entity.Pit;
import com.kalaha.game.entity.Player;
import com.kalaha.game.exception.GameNotFoundException;
import com.kalaha.game.exception.InvalidMoveException;
import com.kalaha.game.repository.GameRepository;
import com.kalaha.game.util.Constants;
import com.kalaha.game.util.KalahaUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class KalahaService {

    private final GameRepository gameRepository;

    public KalahaService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game startNewGame(int noOfStones) {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, noOfStones, Player.PLAYER1));
        }
        pitMap.put(Constants.PLAYER_ONE_HOME, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, Constants.PLAYER_ONE_HOME, 0, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, noOfStones, Player.PLAYER2));
        }
        pitMap.put(Constants.PLAYER_TWO_HOME, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, Constants.PLAYER_TWO_HOME, 0, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(Constants.GAME_IN_PROGRESS);
        game.setNextMove(Constants.PLAYER_ONE);
        gameRepository.save(game);
        return game;
    }

    public Game makeMove(int gameId, int pitId, Integer noOfStones) throws GameNotFoundException, InvalidMoveException {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        validateGame(optionalGame, pitId);
        Game game = computeMove(optionalGame.get(), pitId);
        return checkForWinner(KalahaUtil.createPlayersStats(optionalGame.get()), game, noOfStones);
    }

    private Game computeMove(Game game, int pitId) {
        int currentPlayer = 0;
        currentPlayer = game.getNextMove().equals(Constants.PLAYER_TWO) ? 2 : 1;
        distributeStones(game, pitId, game.getPitMap(), currentPlayer);
        return gameRepository.save(game);
    }

    private Game distributeStones(Game game, int pitId, Map<Integer, Pit> pitMap, int currentPlayer) {

        Pit selectedPit = pitMap.get(pitId);
        int stonesInPit = selectedPit.getStones();
        selectedPit.setStones(0);
        pitMap.put(pitId, selectedPit);
        while (stonesInPit > 0) {
            pitId++;
            if (pitId > 14) {
                pitId = pitId % 14;
            }
            Pit pit = pitMap.get(pitId);
            if (!((pitId == 7 && currentPlayer == 2) || (pitId == 14 && currentPlayer == 1))) {
                pit.setStones(pit.getStones() + 1);
                pitMap.put(pitId, pit);
                stonesInPit--;
            }
        }
        game.setPitMap(pitMap);
        selectNextMove(game, currentPlayer, pitId);
        return game;
    }

    private Game selectNextMove(Game game, int currentPlayer, int pitId) {
        Map<Integer, Pit> pitMap = game.getPitMap();
        Pit lastPit = pitMap.get(pitId);
        int noOfStones = lastPit.getStones();
        if (pitId == 7 && currentPlayer == 1) {
            game.setNextMove(Constants.PLAYER_ONE);
        } else if (pitId == 14 && currentPlayer == 2) {
            game.setNextMove(Constants.PLAYER_TWO);
        } else if (noOfStones == 1 && ((currentPlayer == 1 && lastPit.getPlayer().toString().equals(Constants.PLAYER_ONE_ENUM)) ||
                currentPlayer == 2 && lastPit.getPlayer().toString().equals(Constants.PLAYER_TWO_ENUM))) {
            Pit oppositePit = pitMap.get(14 - pitId);
            int stonesInOppositePit = oppositePit.getStones();
            Pit homePit = currentPlayer == 1 ? pitMap.get(7) : pitMap.get(14);
            homePit.setStones(homePit.getStones() + stonesInOppositePit + 1);
            lastPit.setStones(0);
            oppositePit.setStones(0);
            pitMap.put(14 - pitId, oppositePit);
            pitMap.put(currentPlayer == 1 ? 7 : 14, homePit);
            pitMap.put(pitId, lastPit);
            game.setNextMove(currentPlayer == 1 ? Constants.PLAYER_TWO : Constants.PLAYER_ONE);
        } else {
            game.setNextMove(currentPlayer == 1 ? Constants.PLAYER_TWO : Constants.PLAYER_ONE);
        }
        return game;
    }

    private void validateGame(Optional<Game> game, int pitId) throws GameNotFoundException, InvalidMoveException {
        if (!game.isPresent()) {
            throw new GameNotFoundException("Invalid Game");
        }
        if (pitId < 1 || pitId > 13 || pitId == 7) {
            throw new InvalidMoveException("Invalid Move. Please select valid pits");
        }
        validateNextMove(game.get(), pitId);
    }

    private void validateNextMove(Game game, int pitId) throws InvalidMoveException {
        int nextMove = game.getNextMove();
        if (nextMove == Constants.PLAYER_ONE) {
            if (pitId > 7) {
                throw new InvalidMoveException("Invalid Pit selected. Please select pits from 1-6");
            }
        } else if (nextMove == Constants.PLAYER_TWO) {
            if (pitId < 8) {
                throw new InvalidMoveException("Invalid Pit selected. Please select pits from 8-13");
            }
        }
    }

    private Game checkForWinner(Map<String, Integer> playerStats, Game game, Integer noOfStones) {
        int playerOnePits = playerStats.get(Constants.PLAYER_STATS_ONE_PITS);
        int playerTwoPits = playerStats.get(Constants.PLAYER_STATS_TWO_PITS);
        int playerOneHome = playerStats.get(Constants.PLAYER_STATS_ONE_HOME);
        int playerTwoHome = playerStats.get(Constants.PLAYER_STATS_TWO_HOME);
        Map<Integer, Pit> pitMap = game.getPitMap();
        if (playerOnePits == 0) {
            playerTwoHome = playerTwoHome + playerTwoPits;
            Pit playerTwoHomePit = pitMap.get(Constants.PLAYER_TWO_HOME);
            playerTwoHomePit.setStones(playerTwoHome);
            pitMap.put(Constants.PLAYER_TWO_HOME, playerTwoHomePit);
            game.setPitMap(changePitsToZero(pitMap, Constants.PLAYER_TWO));

        } else if (playerTwoPits == 0) {
            playerOneHome = playerOneHome + playerOnePits;
            Pit playerOneHomePit = pitMap.get(Constants.PLAYER_ONE_HOME);
            playerOneHomePit.setStones(playerOneHome);
            pitMap.put(Constants.PLAYER_ONE_HOME, playerOneHomePit);
            game.setPitMap(changePitsToZero(pitMap, Constants.PLAYER_ONE));
        }

        if (noOfStones > 5) {
            if (playerOneHome > (noOfStones * noOfStones)) {
                game.setWinner(Constants.PLAYER_ONE);
                game.setStatus(Constants.GAME_ENDS);
                return game;
            } else if (playerTwoHome > (noOfStones * noOfStones)) {
                game.setWinner(Constants.PLAYER_TWO);
                game.setStatus(Constants.GAME_ENDS);
                return game;
            }
        }
        if (playerOnePits == 0 || playerTwoPits == 0) {
            if (playerOneHome > playerTwoHome) {
                game.setWinner(Constants.PLAYER_ONE);
                game.setStatus(Constants.GAME_ENDS);

            } else if (playerOneHome < playerTwoHome) {
                game.setWinner(Constants.PLAYER_TWO);
                game.setStatus(Constants.GAME_ENDS);
            }
        }
        return game;
    }

    private Map<Integer, Pit> changePitsToZero(Map<Integer, Pit> pitMap, Integer player) {
        if (player.equals(Constants.PLAYER_ONE)) {
            IntStream.range(1, 6).forEach(i -> {
                Pit pit = pitMap.get(i);
                pit.setStones(0);
                pitMap.put(i, pit);
            });
        }
        if (player.equals(Constants.PLAYER_TWO)) {
            IntStream.range(8, 13).forEach(i -> {
                Pit pit = pitMap.get(i);
                pit.setStones(0);
                pitMap.put(i, pit);
            });
        }
        return pitMap;
    }


}
