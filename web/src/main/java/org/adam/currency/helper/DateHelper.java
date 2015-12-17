package org.adam.currency.helper;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Helper class used for formatting and parsing dates.
 */
public final class DateHelper {
    public static final String APPLICATION_DATE_FORMAT = "dd-MMM-yyyy";
    public static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}-\\w{3}-\\d{4}");

    private DateHelper(){

    }

    /**
     * Converts a given string in expected format of APPLICATION_DATE_FORMAT to LocalDate
     * @param date the date string.
     * @return instance of LocalDate or null if parsing failed.
     */
    public static LocalDate stringToDateTime(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return LocalDate.from(DateTimeFormatter.ofPattern(APPLICATION_DATE_FORMAT).parse(date));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isCorrectDate(String date) {
        return !StringUtils.isBlank(date) && DATE_PATTERN.matcher(date).matches();
    }
}
