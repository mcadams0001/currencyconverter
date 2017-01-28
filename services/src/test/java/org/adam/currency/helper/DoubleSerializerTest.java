package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DoubleSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;


    @Test
    public void testSerialize() throws Exception {
        DoubleSerializer serializer = new DoubleSerializer();
        serializer.serialize(20.35, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo("20.35"));
    }

    @Test
    public void shouldReturnEmptyStringOnNullValue() throws Exception {
        DoubleSerializer serializer = new DoubleSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo(""));

    }

}