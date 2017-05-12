package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.rina.registration.frontend.domain.PendingRegistrationData;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRegistrationsDataService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/request")
public class S1RequestController {
    private final static String S1_PENDING_REGISTRATION_REQUEST = "S1PendingRegistrationRequest";

    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Inject
    public S1RequestController(final RetrieveRegistrationsDataService retrieveRegistrationsDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
    }

    @GetMapping(value = "s1-request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getPendingS1Request(final Model model, final HttpSession httpSession) {
        log.info("Enter getPendingS1Request");
        final List<PendingRegistrationData> pendingRegistrationDataList = retrieveRegistrationsDataService.getPendingS1Requests();
        if (!CollectionUtils.isEmpty(pendingRegistrationDataList)) {
            httpSession.setAttribute(S1_PENDING_REGISTRATION_REQUEST, pendingRegistrationDataList.get(0));
            model.addAttribute("s1request", pendingRegistrationDataList.get(0));
        }
        log.info("Exit getPendingS1Request");
        return "/request/s1-request";
    }
}
