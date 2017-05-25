package uk.doh.oht.frontend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.db.domain.SearchData;
import uk.doh.oht.frontend.domain.CustomerSearchData;
import uk.doh.oht.frontend.domain.SearchResults;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.domain.bucs.BucData;
import uk.doh.oht.rina.domain.bucs.Document;
import uk.doh.oht.rina.domain.documents.S072;
import uk.doh.oht.rina.domain.documents.S073;
import uk.doh.oht.validation.StartDateFormDate;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@Slf4j
@Service
public class SearchService {
    private final static String S072_DOCUMENT_NAME = "S072";
    private final static String S073_DOCUMENT_NAME = "S073";
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;
    private final RetrieveRinaDataService retrieveRinaDataService;

    @Inject
    public SearchService(final RetrieveRegistrationsDataService retrieveRegistrationsDataService,
                         final RetrieveRinaDataService retrieveRinaDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
        this.retrieveRinaDataService = retrieveRinaDataService;
    }

    public SearchResults searchForNextCase() {
        try {
            log.info("Enter searchForNextCase");
            final SearchResults searchResults = new SearchResults();
            List<RegistrationData> registrationDataList = new ArrayList<>();
            searchResults.setRegistrationDataList(registrationDataList);

            for (final OpenCaseSearchResult openCaseSearchResult : retrieveRinaDataService.searchForNextCase()) {
                registrationDataList = retrieveRegistrationsDataService.searchForNextCase(createSearchDataList(openCaseSearchResult));
                if (setSearchResults(registrationDataList, searchResults, openCaseSearchResult)) {
                    break;
                }
            }
            return searchResults;
        } finally {
            log.info("Exit searchForNextCase");
        }
    }

    public SearchResults searchForNextCase(final CustomerSearchData customerSearchData, final OpenCaseSearchResult currentOpenCaseSearchResult) {
        try {
            log.info("Enter searchForNextCase with search data");
            List<RegistrationData> registrationDataList = new ArrayList<>();
            final SearchResults searchResults = new SearchResults();
            searchResults.setRegistrationDataList(registrationDataList);

            for (final OpenCaseSearchResult openCaseSearchResult : Arrays.asList(currentOpenCaseSearchResult)) {
                registrationDataList = retrieveRegistrationsDataService.searchForNextCase(createSearchDataList(openCaseSearchResult.getTraits().getCaseId(), customerSearchData));
                if (setSearchResults(registrationDataList, searchResults, openCaseSearchResult)) {
                    break;
                }
            }
            return searchResults;
        } finally {
            log.info("Exit searchForNextCase with search data");
        }
    }

    private List<SearchData> createSearchDataList(final String caseId, final CustomerSearchData customerSearchData) {
        try {
            log.debug("Enter createSearchDataList with CustomerSearchData");
            final String[] searchFields = customerSearchData.getSearch().split(",");
            final SearchData searchData = SearchData.builder().caseId(caseId).build();
            if (searchFields.length >= 1) {
                searchData.setFirstName(searchFields[0]);
            }
            if (searchFields.length >= 2) {
                searchData.setLastName(searchFields[1]);
            }
            if (searchFields.length >= 3) {
                try {
                    searchData.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(searchFields[2]));
                } catch (ParseException pe) {
                    log.debug("Parse exception :{}", pe);
                }
            }
            if (searchFields.length >= 4) {
                searchData.setNino(searchFields[3]);
            }
            return Arrays.asList(searchData);
        } finally {
            log.debug("Exit createSearchDataList with CustomerSearchData");
        }
    }

    private Boolean setSearchResults(final List<RegistrationData> registrationDataList,
                            final SearchResults searchResults,
                            final OpenCaseSearchResult openCaseSearchResult) {
        try {
            log.debug("Enter setSearchResults");
            if (!CollectionUtils.isEmpty(registrationDataList)) {
                searchResults.setOpenCaseSearchResult(openCaseSearchResult);
                searchResults.setRegistrationDataList(addAdditionalInfoFromRina(registrationDataList, Arrays.asList(openCaseSearchResult)));
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } finally {
            log.debug("Exit setSearchResults");
        }
    }


    private List<SearchData> createSearchDataList(final OpenCaseSearchResult openCaseSearchResult) {
        try {
            log.debug("Enter createSearchDataList with OpenCaseSearchResult");
            return Arrays.asList(createSearchData(openCaseSearchResult.getTraits()));
        } finally {
            log.debug("Exit createSearchDataList with OpenCaseSearchResult");
        }
    }

    private List<SearchData> createSearchDataList(final List<OpenCaseSearchResult> openCasesList) {
        try {
            log.debug("Enter createSearchDataList with List<OpenCaseSearchResult>");
            return openCasesList.stream().map(sd -> createSearchData(sd.getTraits())).collect(toList());
        } finally {
            log.debug("Exit createSearchDataList with List<OpenCaseSearchResult>");
        }
    }

    private SearchData createSearchData(final OpenCaseSearchResult.Traits trait) {
        try {
            log.debug("Enter createSearchData");
            return SearchData.builder()
                    .dateOfBirth(trait.getBirthday())
                    .firstName(trait.getName())
                    .lastName(trait.getSurname())
                    .nino(trait.getLocalPin())
                    .caseId(trait.getCaseId())
                    .build();
        } finally {
            log.debug("Exit createSearchData");
        }
    }

    private List<RegistrationData> addAdditionalInfoFromRina(final List<RegistrationData> registrationDataList,
                                                             final List<OpenCaseSearchResult> openCasesList) {
        try {
            log.debug("Enter addAdditionalInfoFromRina");
            for (final RegistrationData registrationData : registrationDataList) {
                checkDetails(registrationData, openCasesList);
            }
            return registrationDataList;
        } finally {
            log.debug("Exit addAdditionalInfoFromRina");
        }
    }

    private void checkDetails(final RegistrationData registrationData,
                              final List<OpenCaseSearchResult> openCasesList) {
        try {
            log.debug("Enter checkDetails");
            for (final OpenCaseSearchResult openCaseSearchResult : openCasesList) {
                if (registrationData.getCaseId().equals(openCaseSearchResult.getTraits().getCaseId())) {
                    registrationData.setDueDate(openCaseSearchResult.getDueDate());
                    final BucData bucData = retrieveRinaDataService.getCase(registrationData.getCaseId());
                    setDocumentDataInSearchResult(bucData, openCaseSearchResult);
                    registrationData.setS073StartDate(new StartDateFormDate(bucData.getStartDate()));
                    break;
                }
            }
        } finally {
            log.debug("Exit checkDetails");
        }
    }

    private void setDocumentDataInSearchResult(final BucData bucData, final OpenCaseSearchResult openCaseSearchResult) {
        try {
            log.debug("Enter setDocumentDataInSearchResult");
            openCaseSearchResult.setCountryDescription(
                    retrieveRegistrationsDataService.retrieveCountryDescription(openCaseSearchResult.getCountryCode()));
            if (setS072DocumentDataInSearchResult(bucData, openCaseSearchResult)) {
                return;
            }
            setS073DocumentDataInSearchResult(bucData, openCaseSearchResult);
        } finally {
            log.debug("Exit setDocumentDataInSearchResult");
        }
    }

    private void setS073DocumentDataInSearchResult(final BucData bucData, final OpenCaseSearchResult openCaseSearchResult) {
        try {
            log.debug("Enter setS073DocumentDataInSearchResult");
            for (final Document document : bucData.getDocuments()) {
                if (S073_DOCUMENT_NAME.equalsIgnoreCase(document.getType())) {
                    final S073 s073 = retrieveRinaDataService.getS073Document(bucData.getId(), document.getId());
                    openCaseSearchResult.getTraits().setAddress(s073.getAddress().getAddress());
                    openCaseSearchResult.getTraits().setGender(StringUtils.capitalize(s073.getPerson().getPersonIdentification().getSex()));
                }
            }
        } finally {
            log.debug("Exit setS073DocumentDataInSearchResult");
        }
    }

    private Boolean setS072DocumentDataInSearchResult(final BucData bucData, final OpenCaseSearchResult openCaseSearchResult) {
        try {
            log.debug("Enter setS072DocumentDataInSearchResult");
            for (final Document document : bucData.getDocuments()) {
                if (S072_DOCUMENT_NAME.equalsIgnoreCase(document.getType())) {
                    final S072 s072 = retrieveRinaDataService.getS072Document(bucData.getId(), document.getId());
                    openCaseSearchResult.getTraits().setAddress(s072.getPersonAddress().getAddress());
                    openCaseSearchResult.getTraits().setGender(StringUtils.capitalize(s072.getPerson().getPersonIdentification().getSex()));
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } finally {
            log.debug("Exit setS072DocumentDataInSearchResult");
        }
    }
}
