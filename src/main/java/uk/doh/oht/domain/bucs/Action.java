package uk.doh.oht.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action implements Serializable {
    private String template;
    private String typeVersion;
    private String isDocumentRelated;
    private String requiresValidDocument;
    private String displayName;
    private String isCaseRelated;
    private String name;
    private String documentId;
    private String id;
    private String type;
    private String parentDocumentId;
    private Tag tags;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Tag implements Serializable {
        private String category;
        private String type;
    }
}
