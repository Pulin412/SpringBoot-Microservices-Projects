package com.procedure.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.procedure.app.controller.ProcedureScheduleController;
import com.procedure.app.model.Study;
import com.procedure.app.service.PatientService;
import com.procedure.app.service.ProcedureService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

	@Mock
	private PatientService patientService;

	@Mock
	private ProcedureService procedureService;

	@InjectMocks
	private ProcedureScheduleController procedureScheduleController;

	private MockMvc mvc;

	@Before
	public void setup() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		this.mvc = MockMvcBuilders.standaloneSetup(this.procedureScheduleController)
			.setViewResolvers(viewResolver)
			.build();
	}

	@Test
	public void testHome() throws Exception {

		List<Study> list = new ArrayList<Study>();
		list.add(new Study());
		when(this.procedureService.getAllProcedures()).thenReturn(list);

		MockHttpServletResponse response = mvc.perform(get("/")
			.accept(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getForwardedUrl()).contains("index.jsp");
	}

	@Test
	public void testGetAllProcedures() throws Exception {

		List<Study> list = new ArrayList<Study>();
		list.add(new Study());
		when(this.procedureService.getAllProcedures()).thenReturn(list);

		MockHttpServletResponse response = mvc.perform(get("/getAllProcedures")
			.accept(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getForwardedUrl()).contains("procedureDetails.jsp");
	}

}
