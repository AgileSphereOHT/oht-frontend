package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.service.RetrieveRegistrationsDataService;
import uk.doh.oht.frontend.utils.OHTFrontendConstants;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 15/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoMoreCasesControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Mock
    private SecurityContext securityContextMocked;
    @Mock
    private Authentication authenticationMocked;
    @Mock
    private org.springframework.security.core.userdetails.UserDetails principal;

    private UserWorkDetails userWorkDetails;

    @Before
    public void setUp() throws Exception {
        userWorkDetails =  new UserWorkDetails(null, 1l, 2l, 3l, 4l, 5l, 6l);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new NoMoreCasesController(retrieveRegistrationsDataService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        given(authenticationMocked.getPrincipal()).willReturn(principal);
        given(securityContextMocked.getAuthentication()).willReturn(authenticationMocked);
        given(retrieveRegistrationsDataService.retrieveUserWorkData(anyString(), anyString())).willReturn(userWorkDetails);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void testGetNoMoreCasesInQueue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/no-more-cases-in-queue"))
                .andExpect(handler().methodName("getNoMoreCasesInQueue"))
                .andExpect(handler().handlerType(NoMoreCasesController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute(OHTFrontendConstants.DETAILS, userWorkDetails));
    }
}