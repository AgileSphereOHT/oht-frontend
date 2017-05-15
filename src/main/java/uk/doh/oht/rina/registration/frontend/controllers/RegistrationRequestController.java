package uk.doh.oht.rina.registration.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.service.SearchService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationRequestController {
    private final SearchService searchService;

    @Inject
    public RegistrationRequestController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "s1-registration-request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getNextS1Request(final Model model, final HttpSession httpSession) {
        try {
            log.info("Enter getNextS1Request");
            //got to rina and get latest S073
            List<RegistrationData> registrationData = searchService.searchForNextCase();
            if (CollectionUtils.isEmpty(registrationData)) {
                return "redirect:/request/s1-request";
            }
            httpSession.setAttribute("S1RegistrationRequest", registrationData.get(0));
            model.addAttribute("registration", registrationData.get(0));
            return "/registration/s1-registration-request";
        } finally {
            log.info("Exit getNextS1Request");
        }
    }
}
