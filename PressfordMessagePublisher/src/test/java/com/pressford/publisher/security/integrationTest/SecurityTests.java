package com.pressford.publisher.security.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import com.pressford.publisher.controllers.MessageController;

/**
 * @author Pulin
 *
 *         Unit tests for logging and Home page access.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SecurityTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MockHttpServletRequest request;

	@Autowired
	private MessageController messageController;

	private static Model mockedModel;

	@BeforeClass
	public static void setUpUserControllerInstance() {

		mockedModel = mock(Model.class);
	}

	@Test
	public void verifiesLoginPageLoads() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/signin")).andExpect(MockMvcResultMatchers.model().hasNoErrors())
				.andExpect(MockMvcResultMatchers.view().name("login")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testThatRootPageIsSecuredAndRedirectedToSiginin() throws Exception {

		final ResultActions resultActions = mvc.perform(get("/"));
		resultActions.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("http://localhost/signin"));
	}

	@Test
	public void testLoginAndHomeAccess() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("user1").password("user").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		String returnUrl = messageController.home(mockedModel);

		assertThat(returnUrl).isEqualTo("index");
	}
}
