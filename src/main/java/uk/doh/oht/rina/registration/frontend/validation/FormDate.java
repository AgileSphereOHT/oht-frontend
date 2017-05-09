package uk.doh.oht.rina.registration.frontend.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peterwhitehead on 09/05/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDate {

    @JsonIgnore
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String days;
    private String month;
    private String year;

    public Date getDate() {

        if (StringUtils.isEmpty(days) ||
                StringUtils.isEmpty(month) ||
                StringUtils.isEmpty(year)) {
            return null;
        }

        final String formattedDate = String.format("%s-%s-%s", getYear(), getMonth(), getDays());

        try {
            if (StringUtils.isNotEmpty(formattedDate)) {
                dateFormat.setLenient(false);
                return dateFormat.parse(formattedDate);
            }
        } catch (ParseException e) {
            return null;
        }
        return null;
    }

    public void setDate(final Date date) {
        if (date != null) {
            final String[] strDate = dateFormat.format(date).split("-");
            this.days = strDate[2];
            this.month = strDate[1];
            this.year = strDate[0];
        }
    }

    public void setDate(final String date) {
        if (date != null) {
            try {
                final Date newDate = dateFormat.parse(date);
                this.setDate(newDate);
            } catch (ParseException pe) {

            }
        }
    }
}