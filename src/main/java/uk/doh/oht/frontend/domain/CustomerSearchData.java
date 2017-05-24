package uk.doh.oht.frontend.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by peterwhitehead on 23/05/2017.
 */
@Data
public class CustomerSearchData {
    private String searchSelected;

    @NotEmpty(message = "{customerSearch.searchNotBlank}")
    private String search;
}
