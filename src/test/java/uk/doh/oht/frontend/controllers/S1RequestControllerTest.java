package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.db.domain.PendingRegistrationData;
import uk.doh.oht.frontend.service.RetrieveRegistrationsDataService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 15/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class S1RequestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new S1RequestController(retrieveRegistrationsDataService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
    }

    @Test
    public void testGetPendingS1Request() throws Exception {
        final List<PendingRegistrationData> pendingData = new ArrayList<>();
        final PendingRegistrationData pendingRegistrationData = new PendingRegistrationData();
        pendingData.add(pendingRegistrationData);
        given(retrieveRegistrationsDataService.getPendingS1Requests()).willReturn(pendingData);
        mockMvc.perform(MockMvcRequestBuilders.get("/request/s1-request"))
                .andExpect(handler().methodName("getPendingS1Request"))
                .andExpect(handler().handlerType(S1RequestController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("s1request", pendingRegistrationData));
    }

    @Test
    public void testGetPendingS1RequestNoCases() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/request/s1-request"))
                .andExpect(handler().methodName("getPendingS1Request"))
                .andExpect(handler().handlerType(S1RequestController.class))
                .andExpect(status().is3xxRedirection());
    }
}