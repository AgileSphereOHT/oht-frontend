package uk.doh.oht.rina.registration.frontend.service;

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
import uk.doh.oht.rina.registration.frontend.config.DataProperties;
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.domain.SearchData;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RetrieveRegistrationsDataServiceTest {
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private DataProperties dataProperties;

    private List<SearchData> searchDataList;
    private List<RegistrationData> registrationDataList;

    @Before
    public void setUp() throws Exception {
        retrieveRegistrationsDataService = new RetrieveRegistrationsDataService(restTemplate, dataProperties);
        searchDataList = new ArrayList<>();
        final SearchData searchData = new SearchData();
        searchDataList.add(searchData);

        registrationDataList = new ArrayList<>();
        final RegistrationData registrationData = new RegistrationData();
        registrationData.setBenefitType("Pensioner");
        registrationDataList.add(registrationData);
    }

    @Test
    public void testSearchForNextCase() throws Exception {
        ResponseEntity<List<RegistrationData>> responseEntity = new ResponseEntity(registrationDataList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<RegistrationData>>> any())).willReturn(responseEntity);

        List<RegistrationData> returnedRegistrationDataList = retrieveRegistrationsDataService.searchForNextCase(searchDataList);
        assertThat(registrationDataList, is(returnedRegistrationDataList));
    }
}