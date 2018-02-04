package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class LocalDateTimeStringSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @BeforeEach
    void setUp() {
        initMocks(this);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Test
    void testSerialize() throws Exception {
        LocalDateTimeStringSerializer serializer = new LocalDateTimeStringSerializer();
        serializer.serialize(LocalDateTime.of(2016, 1, 30, 19, 30, 15), mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("30-Jan-2016 19:30:15", value);
    }

    @Test
    void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        LocalDateTimeStringSerializer serializer = new LocalDateTimeStringSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("", value);
    }

}