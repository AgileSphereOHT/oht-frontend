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
import uk.doh.oht.rina.registration.frontend.config.RinaProperties;
import uk.doh.oht.rina.registration.frontend.domain.CaseDefinition;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
@Service
public class RetrieveRinaDataService {
    private final RestTemplate restTemplate;
    private final RinaProperties rinaProperties;

    @Inject
    public RetrieveRinaDataService(final RestTemplate restTemplate, final RinaProperties rinaProperties) {
        this.restTemplate = restTemplate;
        this.rinaProperties = rinaProperties;
    }

    public List<CaseDefinition> getAllCases() {
        final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        final ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(rinaProperties.buildRootPath() + "get-all-cases", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        List<Map<String, Object>> data = response.getBody();
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return data.stream().map(p -> new CaseDefinition((String)p.get("id"), (String)p.get("processDefinitionId"))).collect(Collectors.toList());
    }

    public Map<String, Object> getCase(final String caseId) {
        final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        final ResponseEntity<Map<String, Object>> response = restTemplate.exchange(rinaProperties.buildRootPath() + "get-case/" + caseId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return response.getBody();
    }

    public Map<String, Object> getDocument(final String caseId, final String documentId) {
        final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        final ResponseEntity<Map<String, Object>> response = restTemplate.exchange(rinaProperties.buildRootPath() + "get-document/" + caseId + "/" + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return response.getBody();
    }

    public List<Map<String, Object>> searchForNextCase() {
        final HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        //search for all open cases with S073 notification
        final String searchText = "statuses=open";
        final ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(rinaProperties.buildRootPath() + "search-cases?searchText=" + searchText, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        List<Map<String, Object>> data = response.getBody();
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return response.getBody();
    }
}
