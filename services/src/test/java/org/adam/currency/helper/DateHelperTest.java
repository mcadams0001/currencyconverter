package org.adam.currency.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }


    @Test
    void testStringToDateTime() {
        new DateHelper();
        LocalDate localDate = DateHelper.stringToDateTime("12-Dec-2015");
        assertEquals(LocalDate.of(2015, 12, 12), localDate);
    }

    @Test
    void shouldReturnNullOnInvalidDate() {
        LocalDate localDate = DateHelper.stringToDateTime("1112-DEC-2015");
        assertNull(localDate);
    }

    @Test
    void shouldReturnNullOnBlankDate() {
        LocalDate localDate = DateHelper.stringToDateTime(null);
        assertNull(localDate);
    }

    @Test
    void testIsCorrectDate() {
        assertTrue(DateHelper.isCorrectDate("12-DEC-2015"));
        assertFalse(DateHelper.isCorrectDate(null));
        assertFalse(DateHelper.isCorrectDate("abc"));
    }

    @Test
    void shouldFormatDate() {
        LocalDate localDate = LocalDate.of(2016, 1, 30);
        assertEquals("2016-01-30", DateHelper.dateToString(localDate));
    }

    @Test
    void shouldReturnTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertEquals(1454180070L, DateHelper.localDateTimeToLong(localDateTime));
    }

    @Test
    void shouldReturnZeroOnNullDate() {
        assertEquals(0L, DateHelper.localDateTimeToLong(null));
    }

    @Test
    void shouldConvertTimeStampToLocalDateTime() {
        LocalDateTime expected = LocalDateTime.of(2015, 4, 26, 17, 15, 15);
        LocalDateTime localDateTime = DateHelper.timestampToLocalDateTime(1430068515L);
        assertEquals(expected, localDateTime);
    }

    @Test
    void shouldReturnNullOnZeroTimeStamp() {
        assertNull(DateHelper.timestampToLocalDateTime(0L));
    }

    @Test
    void shouldFormatLocalDateTimeToString() {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertEquals("30-Jan-2016 18:54:30", DateHelper.localDateTimeToString(localDateTime));
    }

    @Test
    void shouldConvertLocalDateToApplicationFormat() {
        LocalDate localDate = LocalDate.of(2016, 1, 30);
        assertEquals("30-Jan-2016", DateHelper.localDateToAppString(localDate));
    }

    @Test
    void shouldReturnNullOnPatternConversionException() {
        assertNull(DateHelper.localDateToAppString(null));
    }

    @Test
    void shouldBeInThePast() {
        assertTrue(DateHelper.isPastDate(LocalDate.of(2000, 1, 1)));
    }

    @Test
    void todaysDateIsNotInThePast() {
        assertFalse(DateHelper.isPastDate(LocalDate.now()));
    }

    @Test
    void nullIsTrueInThePast() {
        assertTrue(DateHelper.isPastDate(null));
    }
}