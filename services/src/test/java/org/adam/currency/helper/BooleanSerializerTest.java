package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BooleanSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @Test
    public void testSerialize() throws Exception {
        BooleanSerializer serializer = new BooleanSerializer();
        serializer.serialize(Boolean.TRUE, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo("true"));
    }

    @Test
    public void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        BooleanSerializer serializer = new BooleanSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo(""));
    }
}