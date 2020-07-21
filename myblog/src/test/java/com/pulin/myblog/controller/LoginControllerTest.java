package com.pulin.myblog.controller;

import com.pulin.myblog.controller.LoginController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    LoginController loginController = new LoginController();

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Before
    public void setUp(){
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void login_PrincipalNotNull_ShouldRedirectToHome(){
        Assert.assertEquals(loginController.login((Principal) securityContext.getAuthentication()), "redirect:/home");
    }

    @Test
    public void login_PrincipalNull_ShouldRedirectToLogin(){
        Assert.assertEquals(loginController.login(null), "/login");
    }
}
