package uk.doh.oht.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.doh.oht.db.domain.RegistrationData;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;

import java.util.List;

/**
 * Created by peterwhitehead on 23/05/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResults {
    private List<RegistrationData> registrationDataList;
    private OpenCaseSearchResult openCaseSearchResult;
}
