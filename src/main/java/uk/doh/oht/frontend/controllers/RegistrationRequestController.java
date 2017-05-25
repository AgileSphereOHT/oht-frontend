package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.frontend.domain.CustomerSearchData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.frontend.service.SearchService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;

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

    @GetMapping(value = "/s1-registration-request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getNextS1Request(final Model model, final HttpSession httpSession) {
        try {
            log.info("Enter getNextS1Request");
            //got to rina and get latest S073
            final SearchResults searchResults = searchService.searchForNextCase();
            if (CollectionUtils.isEmpty(searchResults.getRegistrationDataList())) {
                return "redirect:/request/s1-request";
            }
            return getNextPage(model, httpSession, searchResults);
        } finally {
            log.info("Exit getNextS1Request");
        }
    }

    private String getNextPage(final Model model, final HttpSession httpSession, final SearchResults searchResults) {
        final List<RegistrationData> registrationDataList = searchResults.getRegistrationDataList();
        if (registrationDataList.size() == 1) {
            httpSession.setAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST, registrationDataList.get(0));
            return "redirect:/registration/s1-registration";
        }
        httpSession.setAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, registrationDataList);
        httpSession.setAttribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT, searchResults.getOpenCaseSearchResult());
        model.addAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, registrationDataList);
        model.addAttribute(OHTFrontendConstants.CUSTOMER_SEARCH_DATA, new CustomerSearchData());
        model.addAttribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT, searchResults.getOpenCaseSearchResult());
        return "registration/s1-registration-search";
    }

    @GetMapping(value = "/get-s1-registration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getS1Registration(@RequestParam final Long registrationId, final Model model, final HttpSession httpSession) {
        try {
            log.info("getS1Registration getS1Registration");
            final List<RegistrationData> registrationDataList =
                    (List<RegistrationData>) httpSession.getAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS);
            for (final RegistrationData registrationData : registrationDataList) {
                if (registrationData.getRegistrationId() == registrationId) {
                    httpSession.setAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST, registrationData);
                    break;
                }
            }
            return "redirect:/registration/s1-registration";
        } finally {
            log.info("Exit getS1Registration");
        }
    }

    @GetMapping(value = "/s1-registration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String s1Registration(final Model model, final HttpSession httpSession){
        model.addAttribute("registration", httpSession.getAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST));
        httpSession.removeAttribute(OHTFrontendConstants.S1_REGISTRATION_REQUEST);
        return "registration/s1-registration";
    }
}
