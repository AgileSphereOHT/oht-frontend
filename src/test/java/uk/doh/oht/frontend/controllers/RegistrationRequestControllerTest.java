package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.frontend.service.SearchService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationRequestControllerTest {
    private final MockHttpSession session = new MockHttpSession();
    private MockMvc mockMvc;

    private final List<RegistrationData> listData = new ArrayList<>();
    private final RegistrationData registrationData = new RegistrationData();
    private SearchResults searchResults;

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationRequestController(searchService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();

        registrationData.setRegistrationId(1l);
        registrationData.setCountry("UK");
        listData.add(registrationData);
        searchResults = new SearchResults();
        searchResults.setRegistrationDataList(listData);
    }

    @Test
    public void testGetNextS1Request() throws Exception {
        given(searchService.searchForNextCase()).willReturn(searchResults);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/s1-registration-request"))
                .andExpect(handler().methodName("getNextS1Request"))
                .andExpect(handler().handlerType(RegistrationRequestController.class))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testGetS1Registration() throws Exception {
        session.setAttribute(OHTFrontendConstants.PARTIAL_SEARCH_RESULTS, listData);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/get-s1-registration")
                .param("registrationId", "1")
                .session(session))
                .andExpect(handler().methodName("getS1Registration"))
                .andExpect(handler().handlerType(RegistrationRequestController.class))
                .andExpect(status().is3xxRedirection());
    }
}