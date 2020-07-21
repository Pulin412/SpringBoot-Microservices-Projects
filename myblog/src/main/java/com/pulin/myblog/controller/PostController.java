package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Post;
import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.PostService;
import com.pulin.myblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PostController {

    private Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/newPost")
    public String newPost(Principal principal, Model model){
        logger.debug("ENTER METHOD ::: newPost");

        Optional<User> user = userService.findByUsername(principal.getName());
        if(user.isPresent()){
            Post post = new Post();
            post.setUser(user.get());
            model.addAttribute("post",post);
            logger.debug("EXIT METHOD ::: newPost");
            return "/postForm";
        } else{
            logger.error("USER NOT PRESENT.. EXIT METHOD ::: newPost");
            return "/error";
        }
    }

    @PostMapping("/newPost")
    public String createNewPost(@Valid Post post, BindingResult bindingResult){
        logger.debug("ENTER METHOD ::: createNewPost");

        if(bindingResult.hasErrors()) {
            logger.error("BINDING RESULT HAS ERRORS.. EXIT METHOD ::: createNewPost");
            return "/postForm";
        }

        logger.debug("SAVING POST ::: createNewPost");
        postService.save(post);
        logger.debug("POST SAVED ::: createNewPost");
        logger.debug("EXIT METHOD ::: createNewPost");
        return "redirect:/blog/" + post.getUser().getUsername();
    }

    @GetMapping("/post/{id}")
    public String getPostWithId(@PathVariable Long id, Principal principal, Model model){
        logger.debug("ENTER METHOD ::: getPostWithId");

        Optional<Post> optionalPost = postService.findForId(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            if(isPrincipalOwnerOfPost(principal,post)){
                logger.debug("VALID USER ::: isPrincipalOwnerOfPost");
                model.addAttribute("username", principal.getName());
            }
            logger.debug("EXIT METHOD ::: getPostWithId");
            return "/post";
        }
        logger.error("POST NOT PRESENT.. EXIT METHOD ::: getPostWithId");
        return "/error";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable Long id, Principal principal, Model model){
        logger.debug("ENTER METHOD ::: editPost");

        Optional<Post> optionalPost = postService.findForId(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (isPrincipalOwnerOfPost(principal, post)) {
                model.addAttribute("post", post);
                logger.debug("EXIT METHOD ::: editPost");
                return "/postForm";
            } else {
                logger.error("LOGGED IN USER IS NOT THE OWNER OF THE POST.. EXIT METHOD ::: editPost");
                return "/403";
            }
        } else {
            logger.error("POST NOT PRESENT.. EXIT METHOD ::: editPost");
            return "/error";
        }
    }

    @DeleteMapping("post/{id}")
    public String deletePost(@PathVariable Long id, Principal principal){
        logger.debug("ENTER METHOD ::: deletePost");

        Optional<Post> optionalPost = postService.findForId(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (isPrincipalOwnerOfPost(principal, post)) {
                logger.debug("DELETING POST ::: deletePost");
                postService.delete(post);
                logger.debug("POST DELETED ::: deletePost");
                logger.debug("ENTER METHOD ::: deletePost");
                return "redirect:/home";
            } else {
                logger.error("LOGGED IN USER IS NOT THE OWNER OF THE POST.. EXIT METHOD ::: deletePost");
                return "/403";
            }

        } else {
            logger.error("POST NOT PRESENT.. EXIT METHOD ::: deletePost");
            return "/error";
        }
    }

    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        logger.debug("CHECKING IF LOGGED IN USER IS OWNER OF THE POST OR NOT ::: isPrincipalOwnerOfPost");
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }
}
