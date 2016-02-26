package org.adam.currency.domain;


import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class LocalDatePersistenceConverterTest {
    private LocalDatePersistenceConverter converter = new LocalDatePersistenceConverter();

    @Test
    public void testConvertToDatabaseColumn() throws Exception {
        Date date = converter.convertToDatabaseColumn(LocalDate.of(2015, 6, 18));
        assertThat(date, equalTo(new GregorianCalendar(2015, 5, 18).getTime()));
    }

    @Test
    public void shouldConvertToNull() throws Exception {
        assertThat(converter.convertToDatabaseColumn(null), nullValue());
    }


    @Test
    public void testConvertToEntityAttribute() throws Exception {
        LocalDate localDate = converter.convertToEntityAttribute(new Date(new GregorianCalendar(2015, 5, 18).getTime().getTime()));
        assertThat(localDate, equalTo(LocalDate.of(2015, 6, 18)));
    }

    @Test
    public void shouldConvertDateToNull() throws Exception {
        assertThat(converter.convertToEntityAttribute(null), nullValue());
    }
}