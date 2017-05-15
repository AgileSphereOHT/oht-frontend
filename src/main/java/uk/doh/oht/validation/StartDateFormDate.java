package uk.doh.oht.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterwhitehead on 09/05/2017.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StartDateFormDate extends FormDate implements Serializable {
    private StartDateFormDate(final String days, final String month, final String year) {
        super(days, month, year);
    }
    public StartDateFormDate(final Date date) {
        this.setDate(date);
    }
    public StartDateFormDate(final String date) {
        this.setDate(date);
    }
}
