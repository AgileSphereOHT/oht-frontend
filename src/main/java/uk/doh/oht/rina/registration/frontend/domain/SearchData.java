package uk.doh.oht.rina.registration.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by peterwhitehead on 05/05/2017.
 */
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchData {
    private String nino;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String postcode;
    private String caseId;
}
