package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationController {
    @PostMapping(value = "s1-confirm-registration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String confirmRegistration(final Model model) {
        log.info("Enter confirmRegistration");
        return "s1-registration-confirmation";
    }
}
