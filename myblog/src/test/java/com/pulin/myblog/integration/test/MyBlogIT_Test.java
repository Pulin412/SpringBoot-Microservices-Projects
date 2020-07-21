package com.pulin.myblog.integration.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.pulin.myblog.controller.RegistrationController;
import com.pulin.myblog.entity.Role;
import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class MyBlogIT_Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private ObjectMapper mapper;

    @WithMockUser("admin")
    @Test
    public void loginAndHomePageTest() throws Exception {
        this.mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("/registration")));
    }

    @WithMockUser("admin")
    @Test
    public void registerUserTest() throws Exception {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setName("John");
        user.setPassword("password");
        Role role = new Role();
        role.setRole("USER");
        user.setRoles(Collections.singletonList(role));
        when(service.findByEmail(isA(String.class))).thenReturn(Optional.of(user));
        when(service.findByUsername(isA(String.class))).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.save(isA(User.class))).thenReturn(user);
        MultiValueMap<String, String> formParams = toFormParams(user,
                Stream.of("id", "created").collect(Collectors.toSet()));

        this.mockMvc.perform(post("/registration", user)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED).params(formParams)
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("/registration")))
                .andExpect(model().attribute("successMessage", "User has been registered successfully"));
    }

    private MultiValueMap<String, String> toFormParams(Object o, Set<String> excludeFields) throws Exception {
        ObjectReader reader = mapper.readerFor(Map.class);
        Map<String, String> map = reader.readValue(mapper.writeValueAsString(o));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        map.entrySet().stream()
                .filter(e -> !excludeFields.contains(e.getKey()))
                .forEach(e -> multiValueMap.add(e.getKey(), (e.getValue() == null ? "" : String.valueOf(e.getValue()))));
        return multiValueMap;
    }
}
