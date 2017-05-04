package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String displayRetrieveNextCase(final Model model) {
        log.info("Enter displayRetrieveNextCase");
        //add logged in users first name to model
        model.addAttribute("userFirstName", "Peter");
        log.info("Exit displayRetrieveNextCase");
        return "/home";
    }
}
