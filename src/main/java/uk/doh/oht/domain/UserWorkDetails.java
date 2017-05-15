package uk.doh.oht.domain;

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
public class UserWorkDetails implements Serializable {
    private String userFullName;
    private Long numberRequests;
    private Long numberRegistrations;
    private Long numberCancellations;
}
