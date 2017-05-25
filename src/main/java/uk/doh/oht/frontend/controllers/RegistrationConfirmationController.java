package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.service.RetrieveRegistrationsDataService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationConfirmationController {
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public RegistrationConfirmationController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @GetMapping(value = "/s1-registration-confirmation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getS1RequestConfirmation(final Model model, final HttpSession httpSession) {
        try {
            log.info("Enter getS1RequestConfirmation");

            final RegistrationData oldRegistrationData = (RegistrationData) httpSession.getAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST);
            model.addAttribute(OHTFrontendConstants.DETAILS, createConfirmationDetails(oldRegistrationData));
            httpSession.removeAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST);

        } finally {
            log.info("Exit getS1RequestConfirmation");
        }
        return "registration/s1-registration-confirmation";
    }

    @PostMapping(value = "/confirm-s1-registration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String confirmRegistration(final RegistrationData registrationData, final Model model, final HttpSession httpSession) {
        try {
            log.info("Enter confirmRegistration");

            final RegistrationData oldRegistrationData =
                    (RegistrationData) httpSession.getAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST);
            oldRegistrationData.setStartDate(registrationData.getStartDate());
            oldRegistrationData.setModifiedByUserId(
                    ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            retrieveRegistrationsDataService.updateRegistrationData(oldRegistrationData);
        } finally {
            log.info("Exit confirmRegistration");
        }
        return "redirect:/registration/s1-registration-confirmation";
    }

    private UserWorkDetails createConfirmationDetails(final RegistrationData registrationData) {
        log.info("Enter createConfirmationDetails");
        return retrieveRegistrationsDataService.retrieveUserWorkData(
                registrationData.getModifiedByUserId(),
                registrationData.getUserDetails().getFirstName() + " " + registrationData.getUserDetails().getLastName());
    }
}
