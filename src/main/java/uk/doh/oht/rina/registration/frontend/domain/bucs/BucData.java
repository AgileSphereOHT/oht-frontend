package uk.doh.oht.rina.registration.frontend.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.frontend.domain.common.Creator;
import uk.doh.oht.rina.registration.frontend.domain.common.Properties;
import uk.doh.oht.rina.registration.frontend.domain.common.Subject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BucData implements Serializable {
    private Creator creator;
    private List<Document> documents;
    private Subject subject;
    private String processDefinitionName;
    private Boolean sensitive;
    private Date lastUpdate;
    private String id;
    private String applicationRoleId;
    private List<Action> actions;
    private Date startDate;
    private String processDefinitionVersion;
    private Properties properties;
    private List<Participant> participants;
    private String status;
}
