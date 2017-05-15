package uk.doh.oht.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organisation implements Serializable {
    private Date activeSince;
    private String registryNumber;
    private String acronym;
    private String countryCode;
    private String name;
    private String location;
    private String id;
}
