package uk.doh.oht.rina.registration.frontend.controllers;

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
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.domain.UserDetails;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRegistrationsDataService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    private final MockHttpSession session = new MockHttpSession();

    private final RegistrationData oldRegistrationData = new RegistrationData();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationController(retrieveRegistrationsDataService))
                .build();
        oldRegistrationData.setUserDetails(new UserDetails());
    }

    @Test
    public void testConfirmRegistration() throws Exception {
        session.setAttribute("S1Request", oldRegistrationData);
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/s1-confirm-registration")
                .session(session))
                .andExpect(handler().methodName("confirmRegistration"))
                .andExpect(handler().handlerType(RegistrationController.class))
                .andExpect(status().is2xxSuccessful());
    }
}