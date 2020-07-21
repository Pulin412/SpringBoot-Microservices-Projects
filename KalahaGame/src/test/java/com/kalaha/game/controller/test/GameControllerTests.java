/**
 * 
 */
package com.kalaha.game.controller.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kalaha.game.controller.GameController;
import com.kalaha.game.model.Game;
import com.kalaha.game.service.GameService;
import com.kalaha.game.util.Constants;
import com.kalaha.game.util.GameUtility;

/**
 * @author Pulin
 *
 */
public class GameControllerTests extends AbstractGameTest {

	@Mock
	GameService service;

	@InjectMocks
	GameController gameController;

	private Game game;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		game = createNewGame();
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRemoteHost()).thenReturn("localhost");
		Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/games/1/pits/1"));
	}

	@Test
	public void whenNewGameCreated_ThenReturnGameURL() throws Exception {
		String uri = "/games";
		String inputJson = super.mapToJson(game);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("{\"id\":\"1\",\"uri\":\"http://localhost/games/1\"}", content);
	}

	@Test
	public void WhenValidMoveMade_ThenMakeMove() throws Exception {

		mockService();
		String uri = "/games/1/pits/1";
		String inputJson = super.mapToJson(game);
		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content,
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/1\",\"status\":{\"1\":0,\"2\":7,\"3\":7,\"4\":7,\"5\":7,\"6\":7,\"7\":1,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0}}");
	}

	@Test
	public void WhenInvalidGameIdGiven_ThenThrowGameNotFoundException() throws Exception {

		String uri = "/games/20/pits/1";
		String inputJson = super.mapToJson(createNewGame());
		mvc.perform(
				MockMvcRequestBuilders.post("/games").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals("Invalid Game Id given.", errorMessage);
	}

	@Test
	public void WhenInvalidPitSelectedByPlayerOne_ThenThrowInvalidPitSelectedByPlayerOneMessage() throws Exception {
		String uri = "/games/1/pits/10";
		String inputJson = super.mapToJson(createNewGame());
		mvc.perform(
				MockMvcRequestBuilders.post("/games").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.PLAYERONE_INVALID_PITID, errorMessage);
	}

	@Test
	public void WhenInvalidPitSelectedByPlayerTwo_ThenThrowInvalidPitSelectedByPlayerTwoMessage() throws Exception {

		mockService();
		String uri = "/games/1/pits/3";

		game.setPlayerOneHostName("localhost");
		String inputJson = super.mapToJson(game);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.PLAYERTWO_INVALID_PITID, errorMessage);
	}

	@Test
	public void WhenInvalidPitGiven_ThenThrowInvalidPitIdMessage() throws Exception {

		mockService();
		String uri = "/games/1/pits/30";

		game.setPlayerOneHostName("localhost1");
		game.setPlayerTwoHostName("localhost2");
		String inputJson = super.mapToJson(game);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.INVALID_PIT_GIVEN, errorMessage);
	}

	@Test
	public void WhenIncorrectPlayerTakesChance_ThenThrowNotPlayedPitMessage() throws Exception {

		mockService();
		String uri = "/games/1/pits/12";

		game.setPlayerOneHostName("localhost");
		game.setPlayerTwoHostName("localhost2");
		String inputJson = super.mapToJson(game);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.NOT_PLAYER_PIT, errorMessage);
	}

	@Test
	public void WhenMoveNotRetainedButSamePlayerTakesChance_ThenThrowNextPlayerChanceMessage() throws Exception {

		mockService();
		String uri = "/games/1/pits/12";

		game.setPlayerOneHostName("localhost");
		game.setPlayerTwoHostName("localhost1");
		game.setLastMoveBy("localhost");
		String inputJson = super.mapToJson(game);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.NEXT_PLAYER_CHANCE, errorMessage);
	}

	@Test
	public void WhenZeroStonesPresent_ThenThrowNoStonesMessage() throws Exception {

		mockService();
		String uri = "/games/1/pits/2";

		game.setPlayerOneHostName("localhost");
		game.setPlayerTwoHostName("localhost1");
		List<Integer> pits = game.getPits();
		int count = 0;
		for (Integer pit : pits) {
			pits.set(count, 0);
			count++;
		}
		game.setPits(pits);
		String inputJson = super.mapToJson(game);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(game));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String errorMessage = mvcResult.getResolvedException().getMessage();
		assertEquals(Constants.ZERO_STONES, errorMessage);
	}

	@Test
	public void WhenLastStoneLeftAndPitHasZeroStones_ThenAddOwnPitAndOppositePitAndSaveInOwnKalaha() throws Exception {

		mockService();
		String uri = "/games/1/pits/1";

		/*
		 * Create pits with 1 stone each and set stones in pitId 2 as 0, select pitId 1
		 * in request.
		 */
		Game newGame = new Game();
		newGame.setPits(GameUtility.populatePlayersMap(1));
		newGame.setId(1l);

		newGame.setPlayerOneHostName("localhost");
		newGame.setPlayerTwoHostName("localhost1");
		List<Integer> pits = newGame.getPits();
		pits.set(1, 0);
		newGame.setPits(pits);

		String inputJson = super.mapToJson(newGame);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(newGame));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/1\",\"status\":{\"1\":0,\"2\":0,\"3\":1,\"4\":1,\"5\":1,\"6\":1,\"7\":2,\"8\":1,\"9\":1,\"10\":1,\"11\":1,\"12\":0,\"13\":1,\"14\":0}}",
				content);
	}

	@Test
	public void WhenPlayerOneHasZeroStonesLeft_ThenReturnPlayerTwoAsWinner() throws Exception {

		mockService();
		String uri = "/games/1/pits/6";

		/*
		 * Create pits with 0 stones in first 5 pits and 1 stone in 6th pit for player
		 * one to make player two win in the next move.
		 */
		Game newGame = new Game();
		List<Integer> status = new ArrayList<Integer>();

		for (int i = 0; i <= 4; i++) {
			status.add(0);
		}
		status.add(1);
		status.add(3);
		for (int i = 7; i <= 12; i++) {
			status.add(1);
		}
		status.add(5);
		newGame.setPits(status);
		newGame.setId(1l);

		newGame.setPlayerOneHostName("localhost");
		newGame.setPlayerTwoHostName("localhost1");

		String inputJson = super.mapToJson(newGame);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(newGame));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int mvcStatus = mvcResult.getResponse().getStatus();
		assertEquals(200, mvcStatus);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/6\",\"status\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0,\"7\":4,\"8\":0,\"9\":0,\"10\":0,\"11\":0,\"12\":0,\"13\":0,\"14\":11}}",
				content);
	}

	@Test
	public void WhenPlayerOneHasZeroStonesLeftButHasMoreStones_ThenReturnPlayerOneAsWinner() throws Exception {

		mockService();
		String uri = "/games/1/pits/6";

		/*
		 * Create pits with 0 stones in first 5 pits and 1 stone in 6th pit for player
		 * one to make player two win in the next move.
		 */
		Game newGame = new Game();
		List<Integer> status = new ArrayList<Integer>();

		for (int i = 0; i <= 4; i++) {
			status.add(0);
		}
		status.add(1);
		status.add(16);
		for (int i = 7; i <= 12; i++) {
			status.add(1);
		}
		status.add(5);
		newGame.setPits(status);
		newGame.setId(1l);

		newGame.setPlayerOneHostName("localhost");
		newGame.setPlayerTwoHostName("localhost1");

		String inputJson = super.mapToJson(newGame);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(newGame));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int mvcStatus = mvcResult.getResponse().getStatus();
		assertEquals(200, mvcStatus);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/6\",\"status\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0,\"7\":17,\"8\":0,\"9\":0,\"10\":0,\"11\":0,\"12\":0,\"13\":0,\"14\":11}}",
				content);
	}

	@Test
	public void WhenPlayerTwoHasZeroStonesLeft_ThenReturnPlayerOneAsWinner() throws Exception {

		mockService();
		String uri = "/games/1/pits/13";

		/*
		 * Create pits with 0 stones in second player's first 5 pits and 1 stone in 6th
		 * pit for player two, to make player one win in the next move.
		 */
		Game newGame = new Game();
		List<Integer> status = new ArrayList<Integer>();

		for (int i = 0; i <= 5; i++) {
			status.add(1);
		}
		status.add(2);
		for (int i = 7; i <= 11; i++) {
			status.add(0);
		}
		status.add(1);
		status.add(1);
		newGame.setPits(status);
		newGame.setId(1l);

		newGame.setPlayerOneHostName("localhost1");
		newGame.setPlayerTwoHostName("localhost");

		String inputJson = super.mapToJson(newGame);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(newGame));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int mvcStatus = mvcResult.getResponse().getStatus();
		assertEquals(200, mvcStatus);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/13\",\"status\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0,\"7\":8,\"8\":0,\"9\":0,\"10\":0,\"11\":0,\"12\":0,\"13\":0,\"14\":2}}",
				content);
	}

	@Test
	public void WhenPlayerTwoHasZeroStonesLeftButHasMoreStones_ThenReturnPlayerTwoAsWinner() throws Exception {

		mockService();
		String uri = "/games/1/pits/13";

		/*
		 * Create pits with 0 stones in second player's first 5 pits and 1 stone in 6th
		 * pit for player two, to make player one win in the next move.
		 */
		Game newGame = new Game();
		List<Integer> status = new ArrayList<Integer>();

		for (int i = 0; i <= 5; i++) {
			status.add(1);
		}
		status.add(2);
		for (int i = 7; i <= 11; i++) {
			status.add(0);
		}
		status.add(1);
		status.add(15);
		newGame.setPits(status);
		newGame.setId(1l);

		newGame.setPlayerOneHostName("localhost1");
		newGame.setPlayerTwoHostName("localhost");

		String inputJson = super.mapToJson(newGame);

		Mockito.when(service.findById(Mockito.any(Long.class))).thenReturn(Optional.of(newGame));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int mvcStatus = mvcResult.getResponse().getStatus();
		assertEquals(200, mvcStatus);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(
				"{\"id\":1,\"url\":\"http://localhost/games/1/pits/13\",\"status\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0,\"7\":8,\"8\":0,\"9\":0,\"10\":0,\"11\":0,\"12\":0,\"13\":0,\"14\":16}}",
				content);
	}

	private void mockService() {

		MockitoAnnotations.initMocks(this);
		super.mvc = MockMvcBuilders.standaloneSetup(gameController).build();
	}

	private Game createNewGame() {
		Game newGame = new Game();
		newGame.setPits(GameUtility.populatePlayersMap(6));
		newGame.setId(1l);
		return newGame;
	}
}
