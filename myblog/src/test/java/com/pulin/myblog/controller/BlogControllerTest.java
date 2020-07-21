package com.pulin.myblog.controller;

import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.PostService;
import com.pulin.myblog.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BlogControllerTest {

    @InjectMocks
    BlogController blogController;

    @Mock
    UserService userService;

    @Mock
    PostService postService;

    @Mock
    Model model;

    @Mock
    Page page;

    @Before
    public void setUp(){
        User user = new User();
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(postService.findByUserOrderedByDatePageable(Mockito.any(),Mockito.anyInt())).thenReturn(page);
    }

    @Test
    public void getBlogByUsername_UserPresent_RedirectToPosts(){
        Assert.assertEquals("/posts", blogController.getBlogByUsername("admin", 0, model));
    }

    @Test
    public void getBlogByUsername_UserNotPresent_RedirectToError(){
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", blogController.getBlogByUsername("admin", 0, model));
    }
}
