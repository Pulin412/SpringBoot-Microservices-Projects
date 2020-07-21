package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Post;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Mock
    private Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Before
    public void setUp(){
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void newPost_UserNotPresent_RedirectToError(){
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", postController.newPost((Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void newPost_UserPresent_RedirectToPostForm(){
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(Optional.of(new User()));
        Assert.assertEquals("/postForm", postController.newPost((Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void createNewPost_BindingResultHasErrors_RedirectToPostForm(){
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/postForm", postController.createNewPost(new Post(), bindingResult));
    }

    @Test
    public void createNewPost_BindingResultNoErrors_RedirectToBlog(){
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.save(Mockito.any())).thenReturn(post);
        Assert.assertEquals("redirect:/blog/John", postController.createNewPost(post, bindingResult));
    }

    @Test
    public void getPostWithId_PostNotPresent_RedirectToError(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", postController.getPostWithId(1L,(Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void getPostWithId_PostPresentButInvalidUser_RedirectToPost(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Mike", "password");
        Assert.assertEquals("/post", postController.getPostWithId(1L, (Principal) token, model));
    }

    @Test
    public void getPostWithId_PostPresentWithValidUser_RedirectToPost(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("John", "password");
        Assert.assertEquals("/post", postController.getPostWithId(1L, (Principal) token, model));
    }

    @Test
    public void editPost_PostNotPresent_RedirectToError(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", postController.editPost(1L,(Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void editPost_PostPresentButInvalidUser_RedirectTo403(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Mike", "password");
        Assert.assertEquals("/403", postController.editPost(1L,(Principal) token, model));
    }

    @Test
    public void editPost_PostPresentWithValidUser_RedirectToPostForm(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("John", "password");
        Assert.assertEquals("/postForm", postController.editPost(1L, (Principal) token, model));
    }

    @Test
    public void deletePost_PostNotPresent_RedirectToError(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", postController.deletePost(1L,(Principal) securityContext.getAuthentication()));
    }

    @Test
    public void deletePost_PostPresentButInvalidUser_RedirectTo403(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Mike", "password");
        Assert.assertEquals("/403", postController.deletePost(1L,(Principal) token));
    }

    @Test
    public void deletePost_PostPresentWithValidUser_RedirectToHome(){
        Post post = new Post();
        User user = new User();
        user.setUsername("John");
        post.setUser(user);
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(post));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("John", "password");
        Assert.assertEquals("redirect:/home", postController.deletePost(1L, (Principal) token));
    }
}
