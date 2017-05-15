package uk.doh.oht.frontend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.doh.oht.frontend.domain.OpenCaseSearchResult;
import uk.doh.oht.frontend.domain.RegistrationData;
import uk.doh.oht.frontend.domain.SearchData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {
    private final static String CASE_ID_VALUE = "1";

    private SearchService searchService;

    @Mock
    private RetrieveRegistrationsDataService retrieveRegistrationsDataService;
    @Mock
    private RetrieveRinaDataService retrieveRinaDataService;

    List<OpenCaseSearchResult> openCaseSearchResultList;
    List<SearchData> searchDataList;
    List<RegistrationData> registrationDataList;

    @Before
    public void setUp() throws Exception {
        searchService = new SearchService(retrieveRegistrationsDataService, retrieveRinaDataService);

        openCaseSearchResultList = new ArrayList<>();
        final OpenCaseSearchResult openCaseSearchResult = new OpenCaseSearchResult();
        openCaseSearchResult.setTraits(openCaseSearchResult. new Traits());
        openCaseSearchResultList.add(openCaseSearchResult);

        searchDataList = new ArrayList<>();
        final SearchData searchData = new SearchData();
        searchDataList.add(searchData);

        registrationDataList = new ArrayList<>();
        final RegistrationData registrationData = new RegistrationData();
        registrationData.setCaseId(CASE_ID_VALUE);
        registrationDataList.add(registrationData);
    }

    @Test
    public void testSearchForNextCase() throws Exception {
        given(retrieveRinaDataService.searchForNextCase()).willReturn(openCaseSearchResultList);
        given(retrieveRegistrationsDataService.searchForNextCase(searchDataList)).willReturn(registrationDataList);
        final List<RegistrationData> returnedRegistrationDataList = searchService.searchForNextCase();
        assertThat(registrationDataList, is(returnedRegistrationDataList));
    }
}