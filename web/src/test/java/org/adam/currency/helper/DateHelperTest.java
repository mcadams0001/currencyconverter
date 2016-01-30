package org.adam.currency.helper;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class DateHelperTest {

    @Before
    public void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }


    @Test
    public void testStringToDateTime() throws Exception {
        LocalDate localDate = DateHelper.stringToDateTime("12-Dec-2015");
        assertThat(localDate, equalTo(LocalDate.of(2015, 12, 12)));
    }

    @Test
    public void shouldReturnNullOnInvalidDate() throws Exception {
        LocalDate localDate = DateHelper.stringToDateTime("1112-DEC-2015");
        assertThat(localDate, nullValue());
    }


    @Test
    public void testIsCorrectDate() throws Exception {
        assertThat(DateHelper.isCorrectDate("12-DEC-2015"), equalTo(true));
        assertThat(DateHelper.isCorrectDate(null), equalTo(false));
        assertThat(DateHelper.isCorrectDate("abc"), equalTo(false));
    }

    @Test
    public void shouldFormatDate() throws Exception {
        LocalDate localDate = LocalDate.of(2016, 1, 30);
        assertThat(DateHelper.dateToString(localDate), equalTo("2016-01-30"));
    }

    @Test
    public void shouldReturnTimeStamp() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertThat(DateHelper.localDateTimeToLong(localDateTime), equalTo(1454180070L));
    }

    @Test
    public void shouldConvertTimeStampToLocalDateTime() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2015, 4, 26, 17, 15, 15);
        LocalDateTime localDateTime = DateHelper.timestampToLocalDateTime(1430068515L);
        assertThat(localDateTime, equalTo(expected));
    }

    @Test
    public void shouldFormatLocalDateTimeToString() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 30, 18, 54, 30);
        assertThat(DateHelper.localDateTimeToString(localDateTime), equalTo("30-Jan-2016 18:54:30"));
    }




}