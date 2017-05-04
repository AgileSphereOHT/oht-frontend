package uk.doh.oht.rina.registration.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Data
@AllArgsConstructor
public class CaseDefinition {
    private String caseId;
    private String processDefinitionId;
}
