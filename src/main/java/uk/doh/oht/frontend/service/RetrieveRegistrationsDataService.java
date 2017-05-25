package uk.doh.oht.frontend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.db.domain.PendingRegistrationData;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.db.domain.SearchData;
import uk.doh.oht.db.domain.UserWorkDetails;
import uk.doh.oht.frontend.config.DataProperties;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterwhitehead on 05/05/2017.
 */
@Slf4j
@Service
public class RetrieveRegistrationsDataService {
    private final RestTemplate restTemplate;
    private final DataProperties dataProperties;

    @Inject
    public RetrieveRegistrationsDataService(final RestTemplate restTemplate, final DataProperties dataProperties) {
        this.restTemplate = restTemplate;
        this.dataProperties = dataProperties;
    }

    public List<RegistrationData> searchForNextCase(final List<SearchData> searchData) {
        try {
            log.info("Enter searchForNextCase");
            final HttpEntity<List<SearchData>> entity = new HttpEntity<>(searchData, new HttpHeaders());
            final ResponseEntity<List<RegistrationData>> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "retrieve-registrations", HttpMethod.POST, entity, new ParameterizedTypeReference<List<RegistrationData>>() {
                    });
            List<RegistrationData> data = response.getBody();
            if (CollectionUtils.isEmpty(data)) {
                return new ArrayList<>();
            }
            return response.getBody();
        } finally {
            log.info("Exit searchForNextCase");
        }
    }

    public List<PendingRegistrationData> getPendingS1Requests() {
        try {
            log.info("Enter getPendingS1Requests");
            final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
            final ResponseEntity<List<PendingRegistrationData>> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "retrieve-pending-registrations", HttpMethod.POST, entity, new ParameterizedTypeReference<List<PendingRegistrationData>>() {
                    });
            List<PendingRegistrationData> data = response.getBody();
            if (CollectionUtils.isEmpty(data)) {
                return new ArrayList<>();
            }
            return response.getBody();
        } finally {
            log.info("Exit getPendingS1Requests");
        }
    }

    public Boolean updateRegistrationData(final RegistrationData registrationData) {
        try {
            log.info("Enter updateRegistrationData");
            final HttpEntity<RegistrationData> entity = new HttpEntity<>(registrationData, new HttpHeaders());
            final ResponseEntity<Boolean> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "update-registration", HttpMethod.POST, entity, new ParameterizedTypeReference<Boolean>() {
                    });
            return response.getBody();
        } finally {
            log.info("Exit updateRegistrationData");
        }
    }

    public UserWorkDetails retrieveUserWorkData(final String userName, final String fullName) {
        try {
            log.info("Enter retrieveUserWorkData");
            final HttpEntity<String> entity = new HttpEntity<>(userName, new HttpHeaders());
            final ResponseEntity<UserWorkDetails> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "retrieve-user-work-details", HttpMethod.POST, entity, new ParameterizedTypeReference<UserWorkDetails>() {
                    });
            final UserWorkDetails userWorkDetails = response.getBody();
            userWorkDetails.setUserFullName(fullName);
            return userWorkDetails;
        } finally {
            log.info("Exit retrieveUserWorkData");
        }
    }

    public Boolean createS1Request(final PendingRegistrationData pendingRegistrationData) {
        try {
            log.info("Enter createS1Request");
            final HttpEntity<PendingRegistrationData> entity = new HttpEntity<>(pendingRegistrationData, new HttpHeaders());
            final ResponseEntity<Boolean> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "create-s1-request", HttpMethod.POST, entity, new ParameterizedTypeReference<Boolean>() {
                    });
            return response.getBody();
        } finally {
            log.info("Exit createS1Request");
        }
    }

    public String retrieveCountryDescription(final String countryCode) {
        try {
            log.info("Enter retrieveCountryDescription");
            final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
            final ResponseEntity<String> response = restTemplate.exchange(
                    dataProperties.buildRootPath() + "retrieve-country?countryCode=" + countryCode, HttpMethod.GET, entity, new ParameterizedTypeReference<String>() {
                    });
            return response.getBody();
        } finally {
            log.info("Exit retrieveCountryDescription");
        }
    }
}
