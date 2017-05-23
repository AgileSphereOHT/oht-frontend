package uk.doh.oht.frontend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.db.domain.PendingRegistrationData;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.db.domain.SearchData;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.config.DataProperties;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by peterwhitehead on 15/05/2017.
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
    private RegistrationData registrationData;

    @Before
    public void setUp() throws Exception {
        retrieveRegistrationsDataService = new RetrieveRegistrationsDataService(restTemplate, dataProperties);
        searchDataList = new ArrayList<>();
        final SearchData searchData = new SearchData();
        searchDataList.add(searchData);

        registrationDataList = new ArrayList<>();
        registrationData = new RegistrationData();
        registrationData.setBenefitType("Pensioner");
        registrationDataList.add(registrationData);
    }

    @Test
    public void testSearchForNextCase() throws Exception {
        final ResponseEntity<List<RegistrationData>> responseEntity = new ResponseEntity(registrationDataList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<RegistrationData>>> any())).willReturn(responseEntity);

        final List<RegistrationData> returnedRegistrationDataList = retrieveRegistrationsDataService.searchForNextCase(searchDataList);
        assertThat(registrationDataList, is(returnedRegistrationDataList));
    }

    @Test
    public void testGetPendingS1Requests() throws Exception {
        final List<PendingRegistrationData> pendingRegistrationDataList = new ArrayList<>();
        final PendingRegistrationData pendingRegistrationData = new PendingRegistrationData();
        pendingRegistrationDataList.add(pendingRegistrationData);
        final ResponseEntity<List<PendingRegistrationData>> responseEntity = new ResponseEntity(pendingRegistrationDataList, HttpStatus.OK);

        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<PendingRegistrationData>>> any())).willReturn(responseEntity);
        final List<PendingRegistrationData>  pendingRegistrationDataList1 = retrieveRegistrationsDataService.getPendingS1Requests();
        assertThat(pendingRegistrationDataList, is(pendingRegistrationDataList1));
    }

    @Test
    public void testUpdateRegistrationData() throws Exception {
        final ResponseEntity<Boolean> responseEntity = new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Boolean>> any())).willReturn(responseEntity);
        final Boolean response = retrieveRegistrationsDataService.updateRegistrationData(registrationData);
        assertThat(Boolean.TRUE, is(response));
    }

    @Test
    public void testRetrieveUserWorkData() throws Exception {
        final UserWorkDetails userWorkDetails =  new UserWorkDetails(null, 1l, 2l, 3l, 4l, 5l, 6l);
        final ResponseEntity<UserWorkDetails> responseEntity = new ResponseEntity(userWorkDetails, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<UserWorkDetails>> any())).willReturn(responseEntity);
        final UserWorkDetails userWorkDetails1 = retrieveRegistrationsDataService.retrieveUserWorkData("test", null);
        assertThat(userWorkDetails, is(userWorkDetails1));
    }

    @Test
    public void testCreateS1Request() throws Exception {
        final PendingRegistrationData pendingRegistrationData = new PendingRegistrationData();
        final ResponseEntity<Boolean> responseEntity = new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Boolean>> any())).willReturn(responseEntity);

        final Boolean response = retrieveRegistrationsDataService.createS1Request(pendingRegistrationData);
        assertThat(Boolean.TRUE, is(response));
    }
}