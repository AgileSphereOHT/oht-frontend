package uk.doh.oht.rina.registration.frontend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.frontend.config.DataProperties;
import uk.doh.oht.rina.registration.frontend.domain.RegistrationData;
import uk.doh.oht.rina.registration.frontend.domain.SearchData;


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
        final HttpEntity<List<SearchData>> entity = new HttpEntity<>(searchData, new HttpHeaders());
        final ResponseEntity<List<RegistrationData>> response = restTemplate.exchange(
                dataProperties.buildRootPath() + "retrieve-registrations", HttpMethod.POST, entity, new ParameterizedTypeReference<List<RegistrationData>>() {});
        List<RegistrationData> data = response.getBody();
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return response.getBody();
    }

    public Boolean updateRegistrationData(final RegistrationData registrationData) {
        final HttpEntity<RegistrationData> entity = new HttpEntity<>(registrationData, new HttpHeaders());
        final ResponseEntity<Boolean> response = restTemplate.exchange(
                dataProperties.buildRootPath() + "update-registration", HttpMethod.POST, entity, new ParameterizedTypeReference<Boolean>() {});
        return response.getBody();
    }
}
