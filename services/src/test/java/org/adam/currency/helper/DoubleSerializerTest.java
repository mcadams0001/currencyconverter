package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class DoubleSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;


    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testSerialize() throws Exception {
        DoubleSerializer serializer = new DoubleSerializer();
        serializer.serialize(20.35, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("20.35", value);
    }

    @Test
    void shouldReturnEmptyStringOnNullValue() throws Exception {
        DoubleSerializer serializer = new DoubleSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertEquals("", value);

    }

}