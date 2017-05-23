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
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.db.domain.UserDetails;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.service.RetrieveRegistrationsDataService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationConfirmationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;
    @Mock
    private SecurityContext securityContextMocked;
    @Mock
    private Authentication authenticationMocked;
    @Mock
    private org.springframework.security.core.userdetails.UserDetails principal;

    private final MockHttpSession session = new MockHttpSession();

    private final RegistrationData oldRegistrationData = new RegistrationData();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationConfirmationController(retrieveRegistrationsDataService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        oldRegistrationData.setUserDetails(UserDetails.builder().build());
        given(authenticationMocked.getPrincipal()).willReturn(principal);
        given(securityContextMocked.getAuthentication()).willReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void testConfirmRegistration() throws Exception {
        session.setAttribute("S1RegistrationRequest", oldRegistrationData);
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/confirm-s1-registration")
                .session(session))
                .andExpect(handler().methodName("confirmRegistration"))
                .andExpect(handler().handlerType(RegistrationConfirmationController.class))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testGetS1RequestConfirmation() throws Exception {
        final UserWorkDetails userWorkDetails =  new UserWorkDetails(null + " " + null, 1l, 2l, 3l, 4l, 5l, 6l);
        session.setAttribute("S1RegistrationRequest", oldRegistrationData);
        given(retrieveRegistrationsDataService.retrieveUserWorkData(anyString(), anyString())).willReturn(userWorkDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/s1-registration-confirmation")
                .session(session))
                .andExpect(handler().methodName("getS1RequestConfirmation"))
                .andExpect(handler().handlerType(RegistrationConfirmationController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("details", userWorkDetails));
    }
}