package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for Http Servlet.
 */
public class HttpServletHelper {
    public static final Logger LOGGER = Logger.getLogger(HttpServletHelper.class);

    HttpServletHelper() {

    }

    public static HttpHeaders createJsonResponseHeaders(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.contains("MSIE")) {
            responseHeaders.setContentType(MediaType.TEXT_HTML);
        } else {
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        return responseHeaders;
    }

    public static String jsonResponse(Object object) {
        ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "[]";
    }
}
