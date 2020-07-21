package com.pulin.myblog.integration.test;

import com.pulin.myblog.controller.HomeController;
import com.pulin.myblog.entity.Role;
import com.pulin.myblog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MyBlogIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void loginAndHomePageTest(){
        ResponseEntity<String> result = restTemplate.withBasicAuth("admin", "admin")
                .getForEntity("/home", String.class);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    //@Test
    public void registerUserTest(){
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setName("John");
        user.setPassword("password");
        Role role = new Role();
        role.setRole("USER");
        user.setRoles(Collections.singletonList(role));
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        messageConverters.add(converter);
        restTemplate.getRestTemplate().setMessageConverters(messageConverters);
        ResponseEntity<User> result = restTemplate.withBasicAuth("admin", "admin").postForEntity("/registration", user, User.class);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
