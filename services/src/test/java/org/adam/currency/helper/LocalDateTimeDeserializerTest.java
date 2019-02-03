package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDateTimeDeserializerTest {

    @Mock
    private JsonParser mockJsonParser;

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }


    @Test
    void testDeserialize() throws Exception {
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer();
        when(mockJsonParser.getLongValue()).thenReturn(1454180070L);
        LocalDateTime localDateTime = deserializer.deserialize(mockJsonParser, null);
        assertEquals(LocalDateTime.of(2016, 1, 30, 18, 54, 30), localDateTime);
    }
}