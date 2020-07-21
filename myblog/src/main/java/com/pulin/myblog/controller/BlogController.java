package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Post;
import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.PostService;
import com.pulin.myblog.service.UserService;
import com.pulin.myblog.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BlogController {

    private Logger logger = LoggerFactory.getLogger(BlogController.class);

    private UserService userService;
    private PostService postService;

    @Autowired
    public BlogController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/blog/{username}")
    public String getBlogByUsername(@PathVariable String username, @RequestParam(defaultValue = "0") int page, Model model){

        logger.debug("ENTER METHOD ::: getBlogByUsername");
        Optional<User> optionalUser = userService.findByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user,page);
            Pager pager = new Pager(posts);
            model.addAttribute("pager", pager);
            model.addAttribute("user", user);

            logger.debug("EXIT METHOD WITH SUCCESS::: getBlogByUsername");
            return "/posts";
        } else{
            logger.debug("USER NOT PRESENT ERROR ::: getBlogByUsername");
            logger.debug("EXIT METHOD WITH ERROR ::: getBlogByUsername");
            return "/error";
        }
    }
}
