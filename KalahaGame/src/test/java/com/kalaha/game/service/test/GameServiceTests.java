/**
 * 
 */
package com.kalaha.game.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kalaha.game.controller.GameController;
import com.kalaha.game.model.Game;
import com.kalaha.game.service.GameService;

import junit.framework.Assert;

/**
 * @author Pulin
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@WebMvcTest(value = GameController.class, secure = false)
public class GameServiceTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService gameservice;
	
	String mockGameJson = "{\r\n" + 
			"    \"id\": \"1\",\r\n" + 
			"    \"uri\": \"http://localhost:8080/games/1\"\r\n" + 
			"}";

	@Test
	public void createGameTest() throws Exception {
		List<Integer> pits = new ArrayList<Integer>();
		pits.add(1);
		pits.add(2);
		Game mockGame = new Game(pits, "localhost");
		mockGame.setId(1l);

		Mockito.when(gameservice.createUpdateGame(Mockito.anyObject())).thenReturn(mockGame);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/games").accept(MediaType.APPLICATION_JSON)
				.content(mockGameJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
}
