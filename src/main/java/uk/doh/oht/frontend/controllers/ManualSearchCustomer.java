package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import uk.doh.oht.frontend.domain.SearchCustomerData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.frontend.service.SearchService;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
    public String searchCustomer(final Model model, final HttpSession httpSession, final SearchCustomerData searchCustomerData) {
        log.info("Enter searchCustomer");

        final OpenCaseSearchResult openCaseSearchResult = (OpenCaseSearchResult)httpSession.getAttribute("currentSearchResult");
        final SearchResults searchResults = searchService.searchForNextCase(searchCustomerData, openCaseSearchResult);
        model.addAttribute("allPartialSearches", searchResults.getRegistrationDataList());
        model.addAttribute("searchCustomerData", new SearchCustomerData());

        log.info("Exit searchCustomer");
        return "registration/s1-registration-search";
    }
}
