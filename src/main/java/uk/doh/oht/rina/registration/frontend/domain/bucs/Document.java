package uk.doh.oht.rina.registration.frontend.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.frontend.domain.common.Creator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document implements Serializable {
    private Creator creator;
    @JsonIgnore
    private List<Attachment> attachments;
    @JsonIgnore
    private List<Comment> comments;
    private Boolean starter;
    private String displayName;
    private String mimeType;
    private Integer subProcessId;
    private String type;
    private Date creationDate;
    private List<Conversation> conversations;
    private Boolean isUnarchive;
    private String typeVersion;
    private Boolean allowsAttachments;
    private List<Version> versions;
    private Boolean hasBusinessValidation;
    private Date lastUpdate;
    private String parentType;
    private String id;
    private String parentDocumentId;
    private Boolean isFirstDocument;
    private String direction;
    private String status;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Attachment implements Serializable {

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Comment implements Serializable {

    }
}
