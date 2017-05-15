package uk.doh.oht.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterwhitehead on 05/05/2017.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchData implements Serializable {
    private String nino;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String postcode;
    private String caseId;
}
