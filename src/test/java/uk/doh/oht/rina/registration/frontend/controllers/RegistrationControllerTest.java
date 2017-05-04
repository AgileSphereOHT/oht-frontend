package uk.doh.oht.rina.registration.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationController())
                .build();
    }

    @Test
    public void testConfirmRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/s1-confirm-registration"))
                .andExpect(handler().methodName("confirmRegistration"))
                .andExpect(handler().handlerType(RegistrationController.class))
                .andExpect(status().is2xxSuccessful());
    }
}