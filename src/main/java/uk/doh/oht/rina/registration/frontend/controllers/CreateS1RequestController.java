package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.rina.registration.frontend.domain.PendingRegistrationData;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRegistrationsDataService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/request")
public class CreateS1RequestController {
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public CreateS1RequestController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @GetMapping(value = "s1-request-created", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getS1RequestCreated(final Model model, final HttpSession httpSession) {
        log.info("Enter getS1RequestCreated");

        log.info("Exit getS1RequestConfirmation");
        return "/request/s1-request-created";
    }

    @PostMapping(value = "create-s1-request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String createS1Request(final PendingRegistrationData pendingRegistrationData, final Model model, final HttpSession httpSession) {
        log.info("Enter getS1RequestCreated");

        log.info("Exit createS1Request");
        return "redirect:/request/s1-request-created";
    }
}
