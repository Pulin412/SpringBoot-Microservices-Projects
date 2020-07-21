package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Comment;
import com.pulin.myblog.entity.Post;
import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.CommentService;
import com.pulin.myblog.service.PostService;
import com.pulin.myblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    private CommentService commentService;
    private PostService postService;
    private UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/createComment")
    public String createNewComment(@Valid Comment comment, BindingResult bindingResult){
        logger.debug("ENTER METHOD ::: createNewComment");

        if(bindingResult.hasErrors()) {
            logger.error("BINDING RESULT HAS ERRORS.. EXIT METHOD ::: createNewComment");
            return "/commentForm";
        }else{
            logger.debug("SAVING COMMENT ::: createNewComment");
            commentService.save(comment);
            logger.debug("COMMENT SAVED.. EXIT METHOD ::: createNewComment");
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

    @GetMapping("/commentPost/{id}")
    public String commentPostWithId(@PathVariable Long id, Principal principal, Model model){

        logger.debug("ENTER METHOD ::: commentPostWithId");
        Optional<Post> post = postService.findForId(id);
        if(post.isPresent()){
            Optional<User> user = userService.findByUsername(principal.getName());
            if(user.isPresent()){
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());
                model.addAttribute("comment", comment);
                logger.debug("EXIT METHOD ::: commentPostWithId");
                return "/commentForm";
            } else {
                logger.error("USER NOT PRESENT.. EXIT METHOD ::: commentPostWithId");
                return "/error";
            }
        } else{
            logger.error("POST NOT PRESENT.. EXIT METHOD ::: commentPostWithId");
            return "/error";
        }
    }
}

