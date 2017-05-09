package uk.doh.oht.rina.registration.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 09/05/2017.
 */
@Builder
@Data
@AllArgsConstructor
public class ConfirmationDetails implements Serializable {
    private String userFullName;
    private Integer numberRequests;
    private Integer numberRegistrations;
    private Integer numberCancellations;
}
