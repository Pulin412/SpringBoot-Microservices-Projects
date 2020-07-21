package com.pulin.myblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login(Principal principal) {

        logger.debug("ENTER METHOD ::: login");
        if (principal != null) {
            logger.error("PRINCIPAL IS NULL.. EXIT METHOD ::: login");
            return "redirect:/home";
        }
        logger.debug("EXIT METHOD ::: login");
        return "/login";
    }
}
