package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Comment;
import com.pulin.myblog.entity.Post;
import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.CommentService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @InjectMocks
    CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    BindingResult bindingResult;

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
    public void createNewComment_whenBindingResultWithNoErrors_RedirectToPost(){
        Comment comment = new Comment();
        Post post = new Post();
        post.setId(1L);
        comment.setPost(post);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(commentService.save(Mockito.any())).thenReturn(comment);
        Assert.assertEquals("redirect:/post/1", commentController.createNewComment(comment, bindingResult));
    }

    @Test
    public void createNewComment_whenBindingResultWithErrors_RedirectToCommentForm(){
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/commentForm", commentController.createNewComment(new Comment(), bindingResult));
    }

    @Test
    public void commentPostWithId_WhenPostNotPresent_RedirectToError(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals("/error", commentController.commentPostWithId(1L, (Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void commentPostWithId_WhenPostPresentUserNotPresent_RedirectToError(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(new Post()));
        Assert.assertEquals("/error", commentController.commentPostWithId(1L, (Principal) securityContext.getAuthentication(), model));
    }

    @Test
    public void commentPostWithId_WhenPostAndUserPresent_RedirectToCommentForm(){
        Mockito.when(postService.findForId(Mockito.anyLong())).thenReturn(Optional.of(new Post()));
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(Optional.of(new User()));
        Assert.assertEquals("/commentForm", commentController.commentPostWithId(1L, (Principal) securityContext.getAuthentication(), model));
    }

}
