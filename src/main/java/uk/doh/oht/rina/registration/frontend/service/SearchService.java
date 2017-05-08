package uk.doh.oht.rina.registration.frontend.service;

import org.springframework.stereotype.Service;
import uk.doh.oht.rina.registration.frontend.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.domain.SearchData;
import uk.doh.oht.rina.registration.frontend.domain.UserDetails;
import uk.doh.oht.rina.registration.frontend.domain.bucs.BucData;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@Service
public class SearchService {
    private final RetrieveRegistrationsDataService retrieveRegistrationsDataService;
    private final RetrieveRinaDataService retrieveRinaDataService;

    @Inject
    public SearchService(final RetrieveRegistrationsDataService retrieveRegistrationsDataService,
                         final RetrieveRinaDataService retrieveRinaDataService) {
        this.retrieveRegistrationsDataService = retrieveRegistrationsDataService;
        this.retrieveRinaDataService = retrieveRinaDataService;
    }

    public List<RegistrationData> searchForNextCase() {
        final List<OpenCaseSearchResult> openCasesList = retrieveRinaDataService.searchForNextCase();
        final List<RegistrationData> registrationDataList =
                retrieveRegistrationsDataService.searchForNextCase(createSearchDataList(openCasesList));
        return addAdditionalInfoFromRina(registrationDataList, openCasesList);
    }

    private List<SearchData> createSearchDataList(final List<OpenCaseSearchResult> openCasesList) {
        return openCasesList.stream().map(sd -> createSearchData(sd.getTraits())).collect(toList());
    }

    private SearchData createSearchData(final OpenCaseSearchResult.Traits trait) {
        return SearchData.builder()
                .dateOfBirth(trait.getBirthday())
                .firstName(trait.getName())
                .lastName(trait.getSurname())
                .nino(trait.getLocalPin())
                .caseId(trait.getCaseId())
                .build();
    }

    private List<RegistrationData> addAdditionalInfoFromRina(final List<RegistrationData> registrationDataList,
                                                             final List<OpenCaseSearchResult> openCasesList) {
        for (RegistrationData registrationData : registrationDataList) {
            checkDetails(registrationData, openCasesList);
        }
        return registrationDataList;
    }

    private void checkDetails(final RegistrationData registrationData,
                              final List<OpenCaseSearchResult> openCasesList) {
        for (OpenCaseSearchResult openCaseSearchResult : openCasesList) {
            if (registrationData.getCaseId().equals(openCaseSearchResult.getTraits().getCaseId())) {
                registrationData.setDueDate(openCaseSearchResult.getDueDate());
                BucData bucData = retrieveRinaDataService.getCase(registrationData.getCaseId());
                registrationData.setS073StartDate(bucData.getStartDate());
                break;
            }
        }
    }
}
