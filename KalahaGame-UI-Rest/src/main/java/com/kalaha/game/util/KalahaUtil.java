package com.kalaha.game.util;

import com.kalaha.game.entity.Game;
import com.kalaha.game.entity.Pit;
import com.kalaha.game.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class KalahaUtil {

    public static Pit createNewPit(String pitType, Integer position, Integer stones, Player player){
        Pit pit = new Pit();
        pit.setPitType(pitType);
        pit.setPosition(position);
        pit.setStones(stones);
        pit.setPlayer(player);
        return pit;
    }

    public static Map<String, Integer> createPlayersStats(Game game){
        int playerOnePits = 0;
        int playerTwoPits = 0;
        int playerOneHome = 0;
        int playerTwoHome = 0;
        Map<Integer, Pit> pitMap = game.getPitMap();
        Map<String, Integer> playerStats = new HashMap<>();
        int i = 1;
        for (Map.Entry<Integer, Pit> entry: pitMap.entrySet()) {
            if(i < 7){
                playerOnePits = entry.getValue().getStones() + playerOnePits;
            }
            if(i > 7 && i < 14){
                playerTwoPits = entry.getValue().getStones() + playerTwoPits;
            }
            if(i == 7){
                playerOneHome = entry.getValue().getStones();
            }
            if(i == 14){
                playerTwoHome = entry.getValue().getStones();
            }
            i++;
        }
        playerStats.put(Constants.PLAYER_STATS_ONE_PITS, playerOnePits);
        playerStats.put(Constants.PLAYER_STATS_TWO_PITS, playerTwoPits);
        playerStats.put(Constants.PLAYER_STATS_ONE_HOME, playerOneHome);
        playerStats.put(Constants.PLAYER_STATS_TWO_HOME, playerTwoHome);
        return playerStats;
    }
}
