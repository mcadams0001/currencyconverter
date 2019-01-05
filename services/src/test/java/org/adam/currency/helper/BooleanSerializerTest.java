package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BooleanSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @Test
    void testSerialize() throws Exception {
        BooleanSerializer serializer = new BooleanSerializer();
        serializer.serialize(Boolean.TRUE, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("true", value);
    }

    @Test
    void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        BooleanSerializer serializer = new BooleanSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("", value);
    }
}