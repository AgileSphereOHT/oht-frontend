package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.rina.registration.frontend.domain.ConfirmationDetails;
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRegistrationsDataService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationController {
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public RegistrationController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @PostMapping(value = "s1-confirm-registration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String confirmRegistration(final RegistrationData registrationData, final Model model, final HttpSession httpSession) {
        log.info("Enter confirmRegistration");

        final RegistrationData oldRegistrationData = (RegistrationData)httpSession.getAttribute("S1Request");
        oldRegistrationData.setS073StartDate(registrationData.getS073StartDate());
        oldRegistrationData.setStartDate(registrationData.getStartDate());
        retrieveRegistrationsDataService.updateRegistrationData(oldRegistrationData);

        model.addAttribute("details", createConfirmationDetails(oldRegistrationData));
        log.info("Exit confirmRegistration");
        return "s1-registration-confirmation";
    }

    private ConfirmationDetails createConfirmationDetails(final RegistrationData registrationData) {
        return ConfirmationDetails.builder()
                .userFullName(registrationData.getUserDetails().getFirstName() + " " + registrationData.getUserDetails().getLastName())
                .numberRequests(0)
                .numberRegistrations(1)
                .numberCancellations(0)
                .build();
    }
}
