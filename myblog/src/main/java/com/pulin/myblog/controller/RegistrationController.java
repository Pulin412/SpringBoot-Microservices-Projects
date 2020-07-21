package com.pulin.myblog.controller;

import com.pulin.myblog.entity.User;
import com.pulin.myblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        logger.debug("ENTER METHOD ::: registration");

        model.addAttribute("user", new User());

        logger.debug("EXIT METHOD ::: registration");
        return "/registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model){
        logger.debug("ENTER METHOD ::: createNewUser");

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            logger.error("EMAIL ID ALREADY USED ::: createNewUser");

            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            logger.error("USERNAME ALREADY USED ::: createNewUser");
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        if(!bindingResult.hasErrors()){
            logger.debug("REGISTERING USER ::: createNewUser");
            userService.save(user);
            logger.debug("USER SUCCESSFULLY REGISTERED ::: createNewUser");
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }

        logger.debug("EXIT METHOD ::: createNewUser");
        return "/registration";
    }
}
