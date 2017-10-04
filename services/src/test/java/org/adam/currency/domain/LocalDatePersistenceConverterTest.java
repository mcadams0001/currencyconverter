package org.adam.currency.domain;


import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDatePersistenceConverterTest {
    private LocalDatePersistenceConverter converter = new LocalDatePersistenceConverter();

    @Test
    void testConvertToDatabaseColumn() throws Exception {
        Date date = converter.convertToDatabaseColumn(LocalDate.of(2015, 6, 18));
        assertEquals(new GregorianCalendar(2015, 5, 18).getTime(), date);
    }

    @Test
    void shouldConvertToNull() throws Exception {
        assertNull(converter.convertToDatabaseColumn(null));
    }


    @Test
    void testConvertToEntityAttribute() throws Exception {
        LocalDate localDate = converter.convertToEntityAttribute(new Date(new GregorianCalendar(2015, 5, 18).getTime().getTime()));
        assertEquals(LocalDate.of(2015, 6, 18), localDate);
    }

    @Test
    void shouldConvertDateToNull() throws Exception {
        assertNull(converter.convertToEntityAttribute(null));
    }
}