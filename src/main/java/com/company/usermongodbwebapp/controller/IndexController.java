package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RequestMapping({"","/","/index","index"})
    public String getIndexPage(Model model){
        model.addAttribute("users", userService.getUsers().collectList().block() );

        return "index";
    }

}
