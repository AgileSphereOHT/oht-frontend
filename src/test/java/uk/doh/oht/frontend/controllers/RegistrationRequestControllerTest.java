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
import uk.doh.oht.frontend.domain.RegistrationData;
import uk.doh.oht.frontend.service.SearchService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationRequestControllerTest {
    private MockMvc mockMvc;

    private final List<RegistrationData> listData = new ArrayList<>();

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationRequestController(searchService))
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        final RegistrationData registrationData = new RegistrationData();
        registrationData.setCountry("UK");
        listData.add(registrationData);
    }

    @Test
    public void testGetNextS1Request() throws Exception {
        given(searchService.searchForNextCase()).willReturn(listData);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/s1-registration-request"))
                .andExpect(handler().methodName("getNextS1Request"))
                .andExpect(handler().handlerType(RegistrationRequestController.class))
                .andExpect(status().is2xxSuccessful());
    }
}