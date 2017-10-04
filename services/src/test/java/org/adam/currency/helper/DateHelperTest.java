package org.adam.currency.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

class DateHelperTest {

    @BeforeEach
    void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }


    @Test
    void testStringToDateTime() throws Exception {
        new DateHelper();
        LocalDate localDate = DateHelper.stringToDateTime("12-Dec-2015");
        assertEquals(LocalDate.of(2015, 12, 12), localDate);
    }

    @Test
    void shouldReturnNullOnInvalidDate() throws Exception {
        LocalDate localDate = DateHelper.stringToDateTime("1112-DEC-2015");
        assertThat(localDate, nullValue());
    }

    @Test
    void shouldReturnNullOnBlankDate() throws Exception {
        LocalDate localDate = DateHelper.stringToDateTime(null);
        assertThat(localDate, nullValue());
    }

    @Test
    void testIsCorrectDate() throws Exception {
        assertEquals(true, DateHelper.isCorrectDate("12-DEC-2015"));
        assertEquals(false, DateHelper.isCorrectDate(null));
        assertEquals(false, DateHelper.isCorrectDate("abc"));
    }

    @Test
    void shouldFormatDate() throws Exception {
        LocalDate localDate = LocalDate.of(2016, 1, 30);
        assertEquals("2016-01-30", DateHelper.dateToString(localDate));
    }

    @Test
    void shouldReturnTimeStamp() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertEquals(1454180070L, DateHelper.localDateTimeToLong(localDateTime));
    }

    @Test
    void shouldReturnZeroOnNullDate() throws Exception {
        assertEquals(0L, DateHelper.localDateTimeToLong(null));
    }

    @Test
    void shouldConvertTimeStampToLocalDateTime() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2015, 4, 26, 17, 15, 15);
        LocalDateTime localDateTime = DateHelper.timestampToLocalDateTime(1430068515L);
        assertEquals(expected, localDateTime);
    }

    @Test
    void shouldReturnNullOnZeroTimeStamp() throws Exception {
        assertThat(DateHelper.timestampToLocalDateTime(0L), nullValue());
    }

    @Test
    void shouldFormatLocalDateTimeToString() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertEquals("30-Jan-2016 18:54:30", DateHelper.localDateTimeToString(localDateTime));
    }

    @Test
    void shouldConvertLocalDateToApplicationFormat() throws Exception {
        LocalDate localDate = LocalDate.of(2016, 1, 30);
        assertEquals("30-Jan-2016", DateHelper.localDateToAppString(localDate));
    }

    @Test
    void shouldReturnNullOnPatternConversionException() throws Exception {
        assertThat(DateHelper.localDateToAppString(null), nullValue());
    }

    @Test
    void shouldBeInThePast() throws Exception {
        assertEquals(true, DateHelper.isPastDate(LocalDate.of(2000, 1, 1)));
    }

    @Test
    void todaysDateIsNotInThePast() throws Exception {
        assertEquals(false, DateHelper.isPastDate(LocalDate.now()));
    }

    @Test
    void nullIsTrueInThePast() throws Exception {
        assertEquals(true, DateHelper.isPastDate(null));
    }
}