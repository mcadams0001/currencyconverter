package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for Http Servlet.
 */
@Component("httpServletHelper")
public class HttpServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletHelper.class);

    public HttpHeaders createJsonResponseHeaders(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String userAgent = request.getHeader("User-Agent");
        responseHeaders.setContentType(getMediaType(userAgent));
        return responseHeaders;
    }

    private MediaType getMediaType(String userAgent) {
        return isIEBrowser(userAgent) ? MediaType.TEXT_HTML : MediaType.APPLICATION_JSON;
    }

    private boolean isIEBrowser(String userAgent) {
        return userAgent != null && userAgent.contains("MSIE");
    }

    public String jsonResponse(Object object) {
        if (object == null) {
            return "{}";
        }
        ObjectWriter objectWriter = getObjectWriter();
        try {
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "{}";
    }

    ObjectWriter getObjectWriter() {
        return new ObjectMapper().writerWithDefaultPrettyPrinter();
    }
}
