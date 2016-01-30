package org.adam.currency.helper;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Helper class used for formatting and parsing dates.
 */
public final class DateHelper {
    public static final String APPLICATION_DATE_FORMAT = "dd-MMM-yyyy";
    public static final String SERVICE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    public static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}-\\w{3}-\\d{4}");

    private DateHelper() {

    }

    /**
     * Converts a given string in expected format of APPLICATION_DATE_FORMAT to LocalDate
     *
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

    /**
     * Checks the correctness of the date represented as string with format APPLICATION_DATE_FORMAT
     *
     * @param date the string representing a date.
     * @return true if date is correct otherwise false.
     */
    public static boolean isCorrectDate(String date) {
        return !StringUtils.isBlank(date) && DATE_PATTERN.matcher(date).matches();
    }

    /**
     * Formats a date to restful currency service format of SERVICE_DATE_FORMAT
     *
     * @param date the date to be formatted.
     * @return string representing a date in format SERVICE_DATE_FORMAT.
     */
    public static String dateToString(LocalDate date) {
        return DateTimeFormatter.ofPattern(SERVICE_DATE_FORMAT).format(date);
    }

    /**
     * Formats local date time to a DATE_TIME_FORMAT string.
     * @param localDateTime instance to be formatted.
     * @return string with formated date and time.
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(localDateTime);
    }

    /**
     * Converts a given localDateTime to timestamp represented in seconds.
     *
     * @param localDateTime the time to be converted
     * @return a long representation of timestamp in seconds.
     */
    public static long localDateTimeToLong(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0L;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000L;
    }

    /**
     * Convert timestamp represented in seconds to LocalDateTime
     * @param timestamp the timestamp in seconds.
     * @return an instance of LocalDateTime.
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }
}
