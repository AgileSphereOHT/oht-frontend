package uk.doh.oht.rina.registration.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationData {
    private UserDetails userDetails;
    private List<Address> addresses;
    private String benefitType;
    private String issueType;
    private String registrationStatus;
    private String country;
    private Date entitlementDate;
    private Date startDate;
    private String hasForeignPension;
    private String requestedBy;
    private Date dueDate;
    private Date s073StartDate;
    private String caseId;
}
