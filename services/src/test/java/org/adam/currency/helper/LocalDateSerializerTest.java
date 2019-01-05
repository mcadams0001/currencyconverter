package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocalDateSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @BeforeEach
    void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Test
    void testSerialize() throws Exception {
        LocalDateSerializer serializer = new LocalDateSerializer();
        serializer.serialize(LocalDate.of(2016, 1, 30), mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("30-Jan-2016", value);
    }

    @Test
    void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        LocalDateSerializer serializer = new LocalDateSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("", value);
    }
}