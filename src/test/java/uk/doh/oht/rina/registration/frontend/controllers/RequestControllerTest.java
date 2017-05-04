package uk.doh.oht.rina.registration.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRinaDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RequestControllerTest {
    private final static String CASE_ID_VALUE = "1";

    private MockMvc mockMvc;

    private final Map<String, Object> mapData = new HashMap<>();
    private final List< Map<String, Object>> listData = new ArrayList<>();


    @Mock
    private RetrieveRinaDataService retrieveRinaDataService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RequestController(retrieveRinaDataService))
                .build();
        mapData.put(CASE_ID_VALUE, "{test-data}");
        listData.add(mapData);
    }

    @Test
    public void testGetNextS1Request() throws Exception {
        given(retrieveRinaDataService.searchForNextCase()).willReturn(listData);

        mockMvc.perform(MockMvcRequestBuilders.get("/request/s1request"))
                .andExpect(handler().methodName("getNextS1Request"))
                .andExpect(handler().handlerType(RequestController.class))
                .andExpect(status().is2xxSuccessful());
    }
}