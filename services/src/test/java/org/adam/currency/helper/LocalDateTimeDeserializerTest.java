package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeDeserializerTest {

    @Mock
    private JsonParser mockJsonParser;

    @Before
    public void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }


    @Test
    public void testDeserialize() throws Exception {
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer();
        when(mockJsonParser.getLongValue()).thenReturn(1454180070L);
        LocalDateTime localDateTime = deserializer.deserialize(mockJsonParser, null);
        assertThat(localDateTime, equalTo(LocalDateTime.of(2016, 1, 30, 18, 54, 30)));
    }
}