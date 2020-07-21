package com.kalaha.game.testUtils;

import com.kalaha.game.entity.Game;
import com.kalaha.game.entity.Pit;
import com.kalaha.game.entity.Player;
import com.kalaha.game.util.Constants;
import com.kalaha.game.util.KalahaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestUtil {
    public static Optional<Game> createExpectedNewGame() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 0, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER2));
        }
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 14, 0, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> createPlayerTwoWinGame() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 0, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 10, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER2));
        }
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 14, 37, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> createPlayerOneWinGame() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 37, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 0, Player.PLAYER2));
        }
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 14, 10, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> createHomeSkipGame() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 0, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER2));
        }
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 14, 0, Player.PLAYER2));
        pitMap.put(6, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 6, 15, Player.PLAYER1));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> createLastStoneInEmptyPitGame() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 0, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER2));
        }
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 14, 0, Player.PLAYER2));
        pitMap.put(13, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 13, 8, Player.PLAYER2));
        pitMap.put(8, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 8, 0, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> test() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER1));
        }
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 0, Player.PLAYER1));
        for (int i = 8; i < 14; i++) {
            pitMap.put(i, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, i, 6, Player.PLAYER2));
        }
        pitMap.put(8, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 8, 1, Player.PLAYER2));
        pitMap.put(9, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 9, 1, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }

    public static Optional<Game> checkWin() {
        Game game = new Game();
        Map<Integer, Pit> pitMap = new HashMap<>();
        pitMap.put(1, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 1, 1, Player.PLAYER1));
        pitMap.put(2, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 2, 1, Player.PLAYER1));
        pitMap.put(3, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 3, 2, Player.PLAYER1));
        pitMap.put(4, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 4, 2, Player.PLAYER1));
        pitMap.put(5, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 5, 0, Player.PLAYER1));
        pitMap.put(6, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 6, 0, Player.PLAYER1));
        pitMap.put(7, KalahaUtil.createNewPit(Constants.PIT_TYPE_HOME, 7, 35, Player.PLAYER1));
        pitMap.put(8, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 8, 1, Player.PLAYER2));
        pitMap.put(9, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 9, 0, Player.PLAYER2));
        pitMap.put(10, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 10, 2, Player.PLAYER2));
        pitMap.put(11, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 11, 2, Player.PLAYER2));
        pitMap.put(12, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 12, 7, Player.PLAYER2));
        pitMap.put(13, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 13, 14, Player.PLAYER2));
        pitMap.put(14, KalahaUtil.createNewPit(Constants.PIT_TYPE_PIT, 14, 5, Player.PLAYER2));
        game.setPitMap(pitMap);
        game.setStatus(0);
        game.setId(1);
        return Optional.of(game);
    }
}
