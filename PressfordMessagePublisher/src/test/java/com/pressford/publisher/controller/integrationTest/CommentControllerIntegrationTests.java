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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.pressford.publisher.controllers.CommentController;
import com.pressford.publisher.entities.Comment;
import com.pressford.publisher.entities.Message;
import com.pressford.publisher.repositories.MessageRepository;

/**
 * @author Pulin
 *
 *         Integration tests for Comment Controller. Testing end to end
 *         scenarios.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CommentControllerIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MockHttpServletRequest request;

	@Autowired
	private CommentController commentController;

	@Autowired
	private MessageRepository messageRepo;

	private static BindingResult mockedBindingResult;

	private static Model mockedModel;

	private static ModelMap mockedModelMap;

	@BeforeClass
	public static void setUpUserControllerInstance() {

		mockedBindingResult = mock(BindingResult.class);
		mockedModel = mock(Model.class);
		mockedModelMap = mock(ModelMap.class);
	}

	@Test
	public void whenAddComment_thenTestLoginAndReturnToAddCommentGet() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		messageRepo.save(message);

		assertThat(commentController.addComment(mockedModelMap, message.getId())).isEqualTo("add-comment");

	}

	@Test
	public void whenSaveComment_thenTestLoginAndReturnToSaveCommentPost() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Message message = new Message("Spring Boot features part 1", "Dummy text", new Date(), "Matt");

		messageRepo.save(message);

		Comment comment = new Comment("Dummy Text", message);

		when(mockedBindingResult.hasErrors()).thenReturn(false);

		assertThat(commentController.saveComment(comment, mockedBindingResult, mockedModel, message.getId()))
				.isEqualTo("index");

	}

	@Test
	public void whenSaveCommentError_thenTestLoginAndReturnToAddComment() throws Exception {

		RequestBuilder requestBuilder = formLogin().user("admin").password("admin").loginProcessingUrl("/signin");

		HttpSession session = this.mvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();

		request.setSession(session);

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		SecurityContextHolder.setContext(securityContext);

		Comment comment = new Comment("Dummy Text", new Message());

		when(mockedBindingResult.hasErrors()).thenReturn(true);

		assertThat(commentController.saveComment(comment, mockedBindingResult, mockedModel, 1L))
				.isEqualTo("add-comment");

	}
}
