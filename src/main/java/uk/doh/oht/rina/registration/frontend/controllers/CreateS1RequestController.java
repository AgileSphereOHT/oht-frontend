package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.rina.registration.frontend.domain.PendingRegistrationData;
import uk.doh.oht.rina.registration.frontend.domain.UserWorkDetails;
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
    private final static String S1_PENDING_REGISTRATION_REQUEST = "S1PendingRegistrationRequest";
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public CreateS1RequestController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @GetMapping(value = "s1-request-created", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getS1RequestCreated(final Model model, final HttpSession httpSession) {
        log.info("Enter getS1RequestCreated");

        final PendingRegistrationData oldPendingRegistrationData =
                (PendingRegistrationData)httpSession.getAttribute(S1_PENDING_REGISTRATION_REQUEST);
        model.addAttribute("details", createWorkDetails(oldPendingRegistrationData));
        httpSession.removeAttribute(S1_PENDING_REGISTRATION_REQUEST);

        log.info("Exit getS1RequestConfirmation");
        return "/request/s1-request-created";
    }

    @PostMapping(value = "create-s1-request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String createS1Request(final PendingRegistrationData pendingRegistrationData, final Model model, final HttpSession httpSession) {
        log.info("Enter getS1RequestCreated");

        final PendingRegistrationData oldPendingRegistrationData =
                (PendingRegistrationData)httpSession.getAttribute(S1_PENDING_REGISTRATION_REQUEST);
        oldPendingRegistrationData.setStartDate(pendingRegistrationData.getStartDate());
        oldPendingRegistrationData.setModifiedByUserId(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        retrieveRegistrationsDataService.createS1Request(oldPendingRegistrationData);

        log.info("Exit createS1Request");
        return "redirect:/request/s1-request-created";
    }

    private UserWorkDetails createWorkDetails(final PendingRegistrationData pendingRegistrationData) {
        final UserWorkDetails userWorkDetails = retrieveRegistrationsDataService.retrieveUserWorkData(pendingRegistrationData.getModifiedByUserId());
        return UserWorkDetails.builder()
                .userFullName(pendingRegistrationData.getFirstName() + " " + pendingRegistrationData.getLastName())
                .numberRequests(userWorkDetails.getNumberRequests())
                .numberRegistrations(userWorkDetails.getNumberRegistrations())
                .numberCancellations(userWorkDetails.getNumberCancellations())
                .build();
    }
}
