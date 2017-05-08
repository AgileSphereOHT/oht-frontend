package uk.doh.oht.rina.registration.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenCaseSearchResult implements Serializable {
    private String processDefinitionId;
    private Traits traits;
    private String id;
    private String applicationRoleId;
    private Properties properties;
    private String status;
    private Date dueDate;
    private String countryCode;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Traits implements Serializable {
        private Date birthday;
        private String localPin;
        private String surname;
        private String caseId;
        private String name;
        private String flowType;
        private String status;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Properties implements Serializable {
        private String importance;
        private String criticality;
    }
}
