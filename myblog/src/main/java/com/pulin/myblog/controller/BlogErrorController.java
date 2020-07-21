package com.pulin.myblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BlogErrorController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(BlogErrorController.class);
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ModelAndView error() {
        return new ModelAndView("/error");
    }

    @GetMapping("/403")
    public ModelAndView error403() {

        logger.debug("ENTER METHOD ::: error403");
        return new ModelAndView("/403");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
