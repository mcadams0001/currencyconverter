package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeSerializerTest {

    @Mock
    private JsonGenerator mockJsonGenerator;

    @Before
    public void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Test
    public void testSerialize() throws Exception {
        LocalDateTimeStringSerializer serializer = new LocalDateTimeStringSerializer();
        serializer.serialize(LocalDateTime.of(2016, 1, 30, 19, 30, 15), mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo("30-Jan-2016 19:30:15"));
    }

    @Test
    public void shouldSerializeAndReturnEmptyStringOnNullDate() throws Exception {
        LocalDateTimeStringSerializer serializer = new LocalDateTimeStringSerializer();
        serializer.serialize(null, mockJsonGenerator, null);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockJsonGenerator).writeString(stringCaptor.capture());
        String value = stringCaptor.getValue();
        assertThat(value, equalTo(""));
    }

}