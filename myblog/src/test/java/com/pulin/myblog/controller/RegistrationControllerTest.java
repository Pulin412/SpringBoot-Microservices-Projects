package com.pulin.myblog.controller;

import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    @InjectMocks
    RegistrationController registrationController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    BindingResult bindingResult;

    @Test
    public void registration_RedirectToRegistration(){
        Assert.assertEquals("/registration", registrationController.registration(model));
    }

    @Test
    public void createNewUser_EmailAlreadyPresent_RedirectToRegistration(){
        User user = new User();
        user.setEmail("a@gmail.com");
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assert.assertEquals("/registration", registrationController.createNewUser(user, bindingResult, model));
    }

    @Test
    public void registration_UserNameAlreadyPresent_RedirectToRegistration(){
        User user = new User();
        user.setUsername("John");
        Assert.assertEquals("/registration", registrationController.createNewUser(user, bindingResult, model));
    }

    @Test
    public void registration_BindingHasErrors_RedirectToRegistration(){
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/registration", registrationController.createNewUser(new User(), bindingResult, model));
    }

    @Test
    public void registration_BindingHasNoErrors_RedirectToRegistration(){
        Mockito.when(userService.save(Mockito.any())).thenReturn(new User());
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Assert.assertEquals("/registration", registrationController.createNewUser(new User(), bindingResult, model));
    }


}
