package com.pulin.myblog.controller;

import com.pulin.myblog.entity.Post;
import com.pulin.myblog.service.PostService;
import com.pulin.myblog.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page, Model model){

        logger.debug("ENTER METHOD ::: home");
        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);
        model.addAttribute("pager", pager);
        logger.debug("EXIT METHOD ::: home");
        return "/home";
    }
}
