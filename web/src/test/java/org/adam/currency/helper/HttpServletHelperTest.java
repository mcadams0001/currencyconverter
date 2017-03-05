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

    private static final String SEPARATOR = System.getProperty("line.separator");

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
    public void jsonResponseEmpty() throws Exception {
        String response = HttpServletHelper.jsonResponse(null);
        assertEquals("{}", response);
    }

}