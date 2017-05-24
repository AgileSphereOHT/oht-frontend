package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.frontend.domain.CustomerSearchData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.frontend.service.SearchService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 24/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ManualSearchCustomerTest {
    private MockMvc mockMvc;
    private final MockHttpSession session = new MockHttpSession();

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ManualSearchCustomer(searchService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
    }

    @Test
    public void testSearchCustomer() throws Exception {
        final OpenCaseSearchResult openCaseSearchResult = new OpenCaseSearchResult();
        final List<RegistrationData> registrationDataList = new ArrayList<>();
        final SearchResults searchResults = new SearchResults(registrationDataList, openCaseSearchResult);
        session.setAttribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT, openCaseSearchResult);
        given(searchService.searchForNextCase(
                Mockito.<CustomerSearchData> any(), Mockito.<OpenCaseSearchResult> any())).willReturn(searchResults);

        mockMvc.perform(MockMvcRequestBuilders.post("/search-customer")
                .session(session))
                .andExpect(handler().methodName("searchCustomer"))
                .andExpect(handler().handlerType(ManualSearchCustomer.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute(OHTFrontendConstants.CURRENT_INCOMING_SEARCH_RESULT, openCaseSearchResult))
                .andExpect(model().attribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, registrationDataList));
    }
}