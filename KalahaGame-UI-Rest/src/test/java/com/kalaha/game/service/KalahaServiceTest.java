package com.kalaha.game.service;

import com.kalaha.game.entity.Game;
import com.kalaha.game.exception.GameNotFoundException;
import com.kalaha.game.exception.InvalidMoveException;
import com.kalaha.game.repository.GameRepository;
import com.kalaha.game.testUtils.TestUtil;
import com.kalaha.game.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class KalahaServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private KalahaService kalahaService;

    private int noOfStones = 6;

    @Test
    void test_when_StartNewGame_then_validGameStarts() {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.startNewGame(6);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(6, game.getPitMap().get(3).getStones());
    }

    @Test
    void test_when_InvalidGameIdPassed_then_InvalidGameException() {
        Exception exception = Assertions.assertThrows(GameNotFoundException.class, () -> {
            kalahaService.makeMove(0, 0, noOfStones);
        });
        Assertions.assertTrue(exception.getMessage().contains("Invalid Game"));
    }

    @Test
    void test_when_InvalidPitPassed_then_InvalidMoveException() {
        Exception exception = Assertions.assertThrows(InvalidMoveException.class, () -> {
            Mockito.when(gameRepository.findById(1)).thenReturn(TestUtil.createExpectedNewGame());
            kalahaService.makeMove(1, 52, noOfStones);
        });
        Assertions.assertTrue(exception.getMessage().contains("Invalid Move"));
    }

    @Test
    void test_when_InvalidPlayerPitPassed_then_InvalidNextMoveException() {
        Exception exception = Assertions.assertThrows(InvalidMoveException.class, () -> {
            final Optional<Game> expectedGame = TestUtil.createExpectedNewGame();
            Mockito.when(gameRepository.findById(1)).thenReturn(expectedGame);
            expectedGame.get().setNextMove(1);
            kalahaService.makeMove(1, 10, noOfStones);
        });
        Assertions.assertTrue(exception.getMessage().contains("Invalid Pit selected. Please select pits from 1-6"));
    }

    @Test
    void test_when_InvalidPlayerPitPassed_then_InvalidNextMoveException_2() {
        Exception exception = Assertions.assertThrows(InvalidMoveException.class, () -> {
            final Optional<Game> expectedGame = TestUtil.createExpectedNewGame();
            Mockito.when(gameRepository.findById(1)).thenReturn(expectedGame);
            expectedGame.get().setNextMove(2);
            kalahaService.makeMove(1, 2, noOfStones);
        });
        Assertions.assertTrue(exception.getMessage().contains("Invalid Pit selected. Please select pits from 8-13"));
    }

    @Test
    void test_when_PlayerOnePitsAreZero_then_PlayerTwoWins() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createPlayerTwoWinGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 2, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.getStatus());
        Assertions.assertEquals(2, game.getWinner());
    }

    @Test
    void test_when_PlayerTwoPitsAreZero_then_PlayerOneWins() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createPlayerOneWinGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 2, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.getStatus());
        Assertions.assertEquals(1, game.getWinner());
    }

    @Test
    void test_when_PitsAreNotZero_then_GameResumesWithStatusZero() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 2, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertNull(game.getWinner());
    }

    @Test
    void test_when_MakeMove_then_StonesDistributes() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 2, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertEquals(7, game.getPitMap().get(3).getStones());
    }

    @Test
    void test_when_MakeMoveWithBiggerPit_then_StonesDistributes() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        expectedGame.setNextMove(Constants.PLAYER_TWO);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 10, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertEquals(7, game.getPitMap().get(1).getStones());
    }

    @Test
    void test_when_MakeMoveWithPlayerOne_then_PlayerTwoHomeIsSkipped() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createHomeSkipGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 6, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertEquals(0, game.getPitMap().get(14).getStones());
    }

    @Test
    void test_when_LastStoneInPlayerOneHome_then_NextMoveIsOfPlayerOne() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 1, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertEquals(Constants.PLAYER_ONE, game.getNextMove());
    }

    @Test
    void test_when_LastStoneInPlayerTwoHome_then_NextMoveIsOfPlayerTwo() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createExpectedNewGame().get();
        expectedGame.setNextMove(Constants.PLAYER_TWO);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 8, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(0, game.getStatus());
        Assertions.assertEquals(Constants.PLAYER_TWO, game.getNextMove());
    }

    @Test
    void test_when_LastStoneInPlayerTwoEmptyPit_then_AllStonesIn() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.createLastStoneInEmptyPitGame().get();
        expectedGame.setNextMove(Constants.PLAYER_TWO);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 13, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(Constants.PLAYER_ONE, game.getNextMove());
        Assertions.assertEquals(9, game.getPitMap().get(14).getStones());
        Assertions.assertEquals(0, game.getPitMap().get(8).getStones());
        Assertions.assertEquals(0, game.getPitMap().get(6).getStones());
    }

    @Test
    void test_when_LastStoneNotInPlayerTwoEmptyPit_then_NotAllStonesIn() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.test().get();
        expectedGame.setNextMove(Constants.PLAYER_TWO);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 8, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(Constants.PLAYER_ONE, game.getNextMove());
        Assertions.assertEquals(2, game.getPitMap().get(9).getStones());
        Assertions.assertEquals(0, game.getPitMap().get(8).getStones());
    }

    @Test
    void checkWin() throws InvalidMoveException, GameNotFoundException {
        final Game expectedGame = TestUtil.checkWin().get();
        expectedGame.setNextMove(Constants.PLAYER_ONE);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Mockito.when(gameRepository.save(expectedGame)).thenReturn(expectedGame);
        Game game = kalahaService.makeMove(1, 4, noOfStones);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.getStatus());
        Assertions.assertEquals(1, game.getWinner());
    }
}
