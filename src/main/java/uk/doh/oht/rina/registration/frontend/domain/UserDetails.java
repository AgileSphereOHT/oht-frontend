package uk.doh.oht.rina.registration.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 05/05/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails implements Serializable {
    private String nino;
    private String title;
    private String firstName;
    private String otherName;
    private String lastName;
    private String maidenName;
    private Date dateOfBirth;
    private String gender;
    private String nationality;
    private List<ContactDetail> contactDetailList;
}
