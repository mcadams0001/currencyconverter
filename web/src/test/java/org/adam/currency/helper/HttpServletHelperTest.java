package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.adam.currency.fixture.CurrencyFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpServletHelperTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    private HttpServletHelper helper = new HttpServletHelper();

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private ObjectWriter mockObjectMapper;

    @Test
    void createJsonResponseHeaders() {
        when(mockRequest.getHeader("User-Agent")).thenReturn("MSIE");
        HttpHeaders responseHeaders = helper.createJsonResponseHeaders(mockRequest);
        assertEquals(MediaType.TEXT_HTML, responseHeaders.getContentType());
    }

    @Test
    void createJsonResponseHeadersNonIE() {
        when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla");
        HttpHeaders responseHeaders = helper.createJsonResponseHeaders(mockRequest);
        assertEquals(MediaType.APPLICATION_JSON, responseHeaders.getContentType());
    }

    @Test
    void jsonResponse() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currency", CurrencyFixture.EUR);
        String response = helper.jsonResponse(map);
        assertEquals("{" + SEPARATOR +
                "  \"currency\" : {" + SEPARATOR +
                "    \"code\" : \"EUR\"," + SEPARATOR +
                "    \"name\" : \"Euro\"," + SEPARATOR +
                "    \"country\" : {" + SEPARATOR +
                "      \"code\" : \"DEU\"," + SEPARATOR +
                "      \"name\" : \"Germany\"," + SEPARATOR +
                "      \"postCodeRegExp\" : null" + SEPARATOR +
                "    }" + SEPARATOR +
                "  }" + SEPARATOR +
                "}", response.trim());
    }

    @Test
    void jsonResponseEmpty() {
        String response = helper.jsonResponse(null);
        assertEquals("{}", response);
    }

    @Test
    void captureException() throws JsonProcessingException {
        HttpServletHelper spyHelper = spy(helper);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currency", CurrencyFixture.EUR);
        doReturn(mockObjectMapper).when(spyHelper).getObjectWriter();
        when(mockObjectMapper.writeValueAsString(any())).thenThrow(new JsonParseException(null, "failed"));
        String response = spyHelper.jsonResponse(map);
        assertEquals("{}", response);
    }
}