package org.adam.currency.helper;

import org.adam.currency.fixture.CurrencyFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpServletHelperTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Test
    public void createJsonResponseHeaders() throws Exception {
        when(mockRequest.getHeader("User-Agent")).thenReturn("MSIE");
        HttpHeaders responseHeaders = HttpServletHelper.createJsonResponseHeaders(mockRequest);
        assertEquals(MediaType.TEXT_HTML, responseHeaders.getContentType());
    }

    @Test
    public void createJsonResponseHeadersNonIE() throws Exception {
        when(mockRequest.getHeader("User-Agent")).thenReturn("Mozilla");
        HttpHeaders responseHeaders = HttpServletHelper.createJsonResponseHeaders(mockRequest);
        assertEquals(MediaType.APPLICATION_JSON, responseHeaders.getContentType());
    }

    @Test
    public void jsonResponse() throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currency", CurrencyFixture.EUR);
        String response = HttpServletHelper.jsonResponse(map);
        assertEquals("{\r\n" +
                "  \"currency\" : {\r\n" +
                "    \"code\" : \"EUR\",\r\n" +
                "    \"name\" : \"Euro\",\r\n" +
                "    \"country\" : {\r\n" +
                "      \"code\" : \"DEU\",\r\n" +
                "      \"name\" : \"Germany\",\r\n" +
                "      \"postCodeRegExp\" : null\r\n" +
                "    }\r\n" +
                "  }\r\n" +
                "}", response.trim());
    }

    @Test
    public void jsonResponseEmpty() throws Exception {
        String response = HttpServletHelper.jsonResponse(null);
        assertEquals("{}", response);
    }

}