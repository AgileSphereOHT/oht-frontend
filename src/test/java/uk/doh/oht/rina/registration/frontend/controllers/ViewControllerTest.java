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
import uk.doh.oht.rina.registration.frontend.domain.CaseDefinition;
import uk.doh.oht.rina.registration.frontend.service.RetrieveRinaDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by peterwhitehead on 02/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ViewControllerTest {
    private final static String CASE_ID_VALUE = "1";
    private final static String DOCUMENT_ID_VALUE = "1";

    private MockMvc mockMvc;

    @Mock
    private RetrieveRinaDataService retrieveRinaDataService;

    private final Map<String, Object> mapData = new HashMap<>();
    private final List<CaseDefinition> listData = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ViewController(retrieveRinaDataService))
                .build();
        mapData.put(CASE_ID_VALUE, "{test-data}");
        listData.add(new CaseDefinition(CASE_ID_VALUE, DOCUMENT_ID_VALUE));
    }

    @Test
    public void testGetAllCases() throws Exception {
        given(retrieveRinaDataService.getAllCases()).willReturn(listData);

        mockMvc.perform(MockMvcRequestBuilders.get("/view/"))
                .andExpect(handler().methodName("getAllCases"))
                .andExpect(handler().handlerType(ViewController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("allCases", listData));
    }

    @Test
    public void testGetCase() throws Exception {
        given(retrieveRinaDataService.getCase(anyString())).willReturn(mapData);

        mockMvc.perform(MockMvcRequestBuilders.get("/view/case/" + CASE_ID_VALUE))
                .andExpect(handler().methodName("getCase"))
                .andExpect(handler().handlerType(ViewController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("case", mapData));
    }

    @Test
    public void testGetDocument() throws Exception {
        given(retrieveRinaDataService.getDocument(anyString(), anyString())).willReturn(mapData);

        mockMvc.perform(MockMvcRequestBuilders.get("/view/case/" + CASE_ID_VALUE + "/" + DOCUMENT_ID_VALUE))
                .andExpect(handler().methodName("getDocument"))
                .andExpect(handler().handlerType(ViewController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("document", mapData));
    }
}