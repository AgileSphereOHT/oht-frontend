package uk.doh.oht.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWorkDetails implements Serializable {
    private String userFullName;
    private Long numberDailyRequests;
    private Long numberDailyRegistrations;
    private Long numberDailyCancellations;

    private Long numberMonthlyRequests;
    private Long numberMonthlyRegistrations;
    private Long numberMonthlyCancellations;
}
