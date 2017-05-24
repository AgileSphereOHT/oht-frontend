package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.db.domain.PendingRegistrationData;
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
public class CreateS1RequestControllerTest {
    private MockMvc mockMvc;

    private final MockHttpSession session = new MockHttpSession();

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Mock
    private SecurityContext securityContextMocked;
    @Mock
    private Authentication authenticationMocked;
    @Mock
    private org.springframework.security.core.userdetails.UserDetails principal;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CreateS1RequestController(retrieveRegistrationsDataService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        given(authenticationMocked.getPrincipal()).willReturn(principal);
        given(securityContextMocked.getAuthentication()).willReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void testGetS1RequestCreated() throws Exception {
        final PendingRegistrationData pendingRegistrationData = new PendingRegistrationData();
        final UserWorkDetails userWorkDetails =  new UserWorkDetails(null + " " + null, 1l, 2l, 3l, 4l, 5l, 6l);

        session.setAttribute("S1PendingRegistrationRequest", pendingRegistrationData);
        given(retrieveRegistrationsDataService.retrieveUserWorkData(anyString(), anyString())).willReturn(userWorkDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/request/s1-request-created")
                .session(session))
                .andExpect(handler().methodName("getS1RequestCreated"))
                .andExpect(handler().handlerType(CreateS1RequestController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute(OHTFrontendConstants.DETAILS, userWorkDetails));
    }

    @Test
    public void testCreateS1Request() throws Exception {
        final PendingRegistrationData pendingRegistrationData = new PendingRegistrationData();
        session.setAttribute("S1PendingRegistrationRequest", pendingRegistrationData);
        mockMvc.perform(MockMvcRequestBuilders.post("/request/create-s1-request")
                .session(session))
                .andExpect(handler().methodName("createS1Request"))
                .andExpect(handler().handlerType(CreateS1RequestController.class))
                .andExpect(status().is3xxRedirection());
    }
}