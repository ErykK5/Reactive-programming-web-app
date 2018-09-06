package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.model.Education;
import com.company.usermongodbwebapp.services.EducationService;
import com.company.usermongodbwebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@Controller
public class EducationController {

    private static final Logger log = Logger.getLogger( EducationController.class.getName() );

    private  final UserService userService;
    private final EducationService educationService;

    public EducationController(EducationService educationService, UserService userService) {
        this.userService = userService;
        this.educationService = educationService;
    }

    @GetMapping("/user/educations/{userId}")
    public String showEducationList(@PathVariable String userId, Model model ){
        model.addAttribute("user", userService.findById( userId ).block() );
        return "user/educations/list";
    }

    @GetMapping("/user/educations/show/{userId}/{eduId}")
    public String showSpecificEducation(@PathVariable String userId, @PathVariable String eduId, Model model ){
        model.addAttribute("edu", educationService.findByUserIdAndEducationId( userId, eduId  ).block() );
        return "user/educations/show";
    }

    @GetMapping("/user/educations/update/{userId}/{eduId}")
    public String updateEducation(@PathVariable String userId, @PathVariable String eduId, Model model ){
        model.addAttribute("edu", educationService.findByUserIdAndEducationId( userId, eduId ).block() );
        return "user/educations/update";
    }

    @GetMapping("/user/educations/new/{userId}")
    public String newEducation(@PathVariable String userId, Model model ){
        log.info( "Created education for user id: " + userId );
        Education e = new Education();
        e.setUser( userService.findById( userId ).block() );
        log.info( "newEducation: educations user id: " + e.getUser().getId() );
        model.addAttribute("edu", e );
        model.addAttribute("user", userService.findById( userId ).block() );            //check without .block()
        return "user/educations/new";
    }

    @PostMapping("/user/educations/{userId}")
    public String saveOrUpdateEducations(@PathVariable String userId, @ModelAttribute Education education){
        education.setUser( userService.findById( userId ).block() );
        Education createdEducation = educationService.createEducation(education).block();
        log.info( "Created education id: " + createdEducation.getId() + " from user id: " + userId );
        return "redirect:/user/educations/" + userId;
    }

    @GetMapping("/user/educations/delete/{userId}/{eduId}")
    public String deleteByIdEducations(@PathVariable String userId, @PathVariable String eduId ){
        educationService.deleteById( userId, eduId ).block();
        return "redirect:/user/educations/" + userId;
    }
}