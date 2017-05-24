package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.frontend.domain.CustomerSearchData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.frontend.service.SearchService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by peterwhitehead on 23/05/2017.
 */
@Controller
@Slf4j
public class ManualSearchCustomer {
    private final SearchService searchService;

    @Inject
    public ManualSearchCustomer(final SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(value = "/search-customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String searchCustomer(final Model model,
                                 final HttpSession httpSession,
                                 @Valid final CustomerSearchData customerSearchData,
                                 final BindingResult bindingResult) {
        log.info("Enter searchCustomer");

        final OpenCaseSearchResult openCaseSearchResult =
                (OpenCaseSearchResult)httpSession.getAttribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT);
        model.addAttribute(OHTFrontendConstants.CUSTOMER_SEARCH_DATA, customerSearchData);
        model.addAttribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT, openCaseSearchResult);

        if (bindingResult.hasErrors()) {
            log.error("Validation failed: " + bindingResult.toString());
            model.addAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, new ArrayList<RegistrationData>());
        } else {
            final SearchResults searchResults = searchService.searchForNextCase(customerSearchData, openCaseSearchResult);
            model.addAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, searchResults.getRegistrationDataList());
        }

        log.info("Exit searchCustomer");
        return "registration/s1-registration-search";
    }
}
