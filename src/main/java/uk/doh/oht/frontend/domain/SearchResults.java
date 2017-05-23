package uk.doh.oht.frontend.domain;

import lombok.Data;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;

import java.util.List;

/**
 * Created by peterwhitehead on 23/05/2017.
 */
@Data
public class SearchResults {
    private List<RegistrationData> registrationDataList;
    private OpenCaseSearchResult openCaseSearchResult;
}
