package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @Before
    public void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Test
    public void testSerialize() throws Exception {
        LocalDateSerializer serializer = new LocalDateSerializer();
        serializer.serialize(LocalDate.of(2016, 1, 30), mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo("30-Jan-2016"));
    }

    @Test
    public void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        LocalDateSerializer serializer = new LocalDateSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo(""));
    }
}