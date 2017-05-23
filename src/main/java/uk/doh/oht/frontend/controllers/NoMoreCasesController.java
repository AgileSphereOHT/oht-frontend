package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.service.RetrieveRegistrationsDataService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
public class NoMoreCasesController {
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public NoMoreCasesController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @GetMapping(value = "/no-more-cases-in-queue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getNoMoreCasesInQueue(final Model model, final HttpSession httpSession) {
        log.info("Enter getNoMoreCasesInQueue");

        model.addAttribute("details", createWorkDetails());

        log.info("Exit getNoMoreCasesInQueue");
        return "no-more-cases-in-queue";
    }

    private UserWorkDetails createWorkDetails() {
        final String userName =
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return retrieveRegistrationsDataService.retrieveUserWorkData(userName, null);
    }
}
