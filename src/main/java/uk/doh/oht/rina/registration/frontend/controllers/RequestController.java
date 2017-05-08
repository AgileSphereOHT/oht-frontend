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
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Controller
@Slf4j
@RequestMapping("/request")
public class RequestController {
    private final SearchService searchService;

    @Inject
    public RequestController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "s1request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getNextS1Request(final Model model) {
        log.info("Enter getNextS1Request");
        //got to rina and get latest S073
        List<RegistrationData> registrationData = searchService.searchForNextCase();
        model.addAttribute("registration", CollectionUtils.isEmpty(registrationData) ? null : registrationData.get(0));
        log.info("Exit getNextS1Request");
        return "s1-registration-request";
    }
}
