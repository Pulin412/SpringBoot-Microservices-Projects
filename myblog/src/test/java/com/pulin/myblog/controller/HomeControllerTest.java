package com.pulin.myblog.controller;

import com.pulin.myblog.controller.HomeController;
import com.pulin.myblog.entity.Post;
import com.pulin.myblog.service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @InjectMocks
    HomeController homeController;

    @Mock
    PostService postService;

    @Mock
    Model model;

    @Mock
    Page page;

    @Test
    public void home_WhenHomePageCalled_RedirectToHome(){
        Mockito.when(postService.findAllOrderedByDatePageable(Mockito.anyInt())).thenReturn(page);
        Assert.assertEquals("/home", homeController.home(5, model));
    }
}
