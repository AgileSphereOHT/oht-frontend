package uk.doh.oht.frontend.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Creator implements Serializable {
    private String name;
    private Organisation organisation;
    private String id;
    private String type;
}
