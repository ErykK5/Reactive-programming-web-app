package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class UserController {

    private static final Logger log = Logger.getLogger( EducationController.class.getName() );

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById( new String(id) ).block() );
        return "user/show";
    }

    @GetMapping("/user/new")
    public String createUser( Model model ){
        model.addAttribute("user", new User() );
        return "user/userform";
    }

    @PostMapping("/user")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if( bindingResult.hasErrors() ){
            bindingResult.getAllErrors().forEach( objectError -> log.info("ERROR: " + objectError.toString()));
            return "user/userform";
        }
        User user1 = userService.createUser(user).block();
        log.info("LOGGER: Save user " + user1.getId() );
        return "redirect:/";
    }

    @GetMapping("/user/update/{id}")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById( id ).block() );
        return "user/userform";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable String id ){
        userService.deleteUser( id );
        return "redirect:/";
    }
}