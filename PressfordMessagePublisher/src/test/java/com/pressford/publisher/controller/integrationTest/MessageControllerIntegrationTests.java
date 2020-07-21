/**
 *
 */
package com.pressford.publisher.controller.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.pressford.publisher.controllers.MessageController;
import com.pressford.publisher.entities.Message;

/**
 * @author Pulin
 *
 *         Integration tests for Message Controller. Testing end to end
 *         scenarios.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MessageControllerIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MockHttpServletRequest request;

	@Autowired
	private MessageController messageController;

	private static BindingResult mockedBindingResult;

	private static Model mockedModel;

	@BeforeClass
	public static void setUpUserControllerInstance() {

		mockedBindingResult = mock(BindingResult.class);
		mockedModel = mock(Model.class);
	}

	@Test
	public void testLoginAndAddMessageAccess() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("user1").password("user").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message mockedMessage = mock(Message.class);

		String returnUrl = messageController.addMessage(mockedMessage);

		assertThat(returnUrl).isEqualTo("add-message");

	}

	@Test
	public void whenNoBindingErrors_thenTestLoginAndAddMessageOnPost() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		when(mockedBindingResult.hasErrors()).thenReturn(false);

		assertThat(messageController.addMessage(message, mockedBindingResult, mockedModel)).isEqualTo("index");

	}

	@Test
	public void whenBindingErrors_thenTestLoginAndReturnToAddMessageGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		when(mockedBindingResult.hasErrors()).thenReturn(true);

		assertThat(messageController.addMessage(message, mockedBindingResult, mockedModel)).isEqualTo("add-message");

	}

	// Update message

	@Test
	public void whenUpdateMessage_thenTestLoginAndReturnToShowUpdateGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		assertThat(messageController.showUpdateForm(1L, mockedModel)).isEqualTo("update-message");

	}

	@Test
	public void whenUpdateMessage_thenTestLoginAndReturnToUpdateMessagePost() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		when(mockedBindingResult.hasErrors()).thenReturn(false);

		assertThat(messageController.updateMessage(1L, message, mockedBindingResult, mockedModel)).isEqualTo("index");

	}

	@Test
	public void whenUpdateMessageError_thenTestLoginAndReturnToUpdateMessagePost() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		when(mockedBindingResult.hasErrors()).thenReturn(true);

		assertThat(messageController.updateMessage(1L, message, mockedBindingResult, mockedModel))
				.isEqualTo("update-message");

	}

	// Delete Message
	@Test
	public void whenDeleteMessage_thenTestLoginAndReturnToDeleteMessageGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		assertThat(messageController.deleteMessage(1L, mockedModel)).isEqualTo("index");

	}

	@Test
	public void whenContactUs_thenContactUsGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		assertThat(messageController.contact()).isEqualTo("contact");

	}

	@Test
	public void whenAboutUs_thenAboutUsGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		assertThat(messageController.about()).isEqualTo("about");

	}
}