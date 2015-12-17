package org.adam.currency.helper;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class DateHelperTest {

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
}