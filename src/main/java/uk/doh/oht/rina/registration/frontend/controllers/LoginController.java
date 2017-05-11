package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by peterwhitehead on 11/05/2017.
 */
@Controller
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage() {
        log.debug("Login page viewed");
        return "login";
    }
}
