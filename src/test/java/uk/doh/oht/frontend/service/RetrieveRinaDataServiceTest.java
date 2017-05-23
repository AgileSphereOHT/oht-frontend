package uk.doh.oht.frontend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.frontend.config.RinaProperties;
import uk.doh.oht.frontend.domain.CaseDefinition;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.domain.bucs.BucData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by peterwhitehead on 15/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RetrieveRinaDataServiceTest {
    private RetrieveRinaDataService retrieveRinaDataService;
    private final static String CASE_ID_VALUE = "1";
    private final static String DOCUMENT_ID_VALUE = "1";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RinaProperties rinaProperties;

    private final Map<String, Object> mapData = new HashMap<>();
    private final BucData bucData = new BucData();
    private final List<Map<String, Object>> listData = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        retrieveRinaDataService = new RetrieveRinaDataService(restTemplate, rinaProperties);
        bucData.setId(CASE_ID_VALUE);
        bucData.setProcessDefinitionName("Test");
        mapData.put("id", CASE_ID_VALUE);
        mapData.put("processDefinitionId", DOCUMENT_ID_VALUE);
        listData.add(mapData);
    }

    @Test
    public void testGetAllCases() throws Exception {
        final ResponseEntity<List<Map<String, Object>>> responseEntity = new ResponseEntity(listData, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<Map<String, Object>>>> any())).willReturn(responseEntity);
        final List<CaseDefinition> list = retrieveRinaDataService.getAllCases();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getCaseId(), is(CASE_ID_VALUE));
        assertThat(list.get(0).getProcessDefinitionId(), is(DOCUMENT_ID_VALUE));
    }

    @Test
    public void testGetCase() throws Exception {
        final ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity(bucData, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Map<String, Object>>> any())).willReturn(responseEntity);
        final BucData data = retrieveRinaDataService.getCase(CASE_ID_VALUE);
        assertThat(data.getId(), is(CASE_ID_VALUE));
        assertThat(data.getProcessDefinitionName(), is("Test"));
    }

    @Test
    public void testGetDocument() throws Exception {
        final ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity(mapData, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Map<String, Object>>> any())).willReturn(responseEntity);
        final Map<String, Object> data = retrieveRinaDataService.getDocument(CASE_ID_VALUE, DOCUMENT_ID_VALUE);
        assertThat(data.size(), is(2));
        assertThat(data.get("id"), is(CASE_ID_VALUE));
        assertThat(data.get("processDefinitionId"), is(DOCUMENT_ID_VALUE));
    }

    @Test
    public void testSearchForNextCase() throws Exception {
        final List<OpenCaseSearchResult> openCaseSearchResultList = new ArrayList<>();
        final OpenCaseSearchResult openCaseSearchResult = new OpenCaseSearchResult();
        openCaseSearchResult.setTraits(openCaseSearchResult. new Traits());
        openCaseSearchResultList.add(openCaseSearchResult);

        final ResponseEntity<List<OpenCaseSearchResult>> responseEntity = new ResponseEntity(openCaseSearchResultList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<OpenCaseSearchResult>>> any())).willReturn(responseEntity);
        final List<OpenCaseSearchResult> openCases = retrieveRinaDataService.searchForNextCase();
        assertThat(openCaseSearchResultList, is(openCases));
    }
}