package org.adam.currency.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class VersionServletTest {

    @Spy
    private VersionServlet servlet = new VersionServlet();

    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private ServletOutputStream mockServletOutputStream;
    @Mock
    private ServletConfig mockServletConfig;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void getVersion() throws Exception {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        servlet.doGet(mockRequest, mockResponse);
        verify(mockServletOutputStream).print("2.0.0");
    }

    @Test
    void throwServletException() throws Exception {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        doThrow(new IOException("failure")).when(mockServletOutputStream).print(anyString());
        assertThrows(ServletException.class, () -> servlet.doGet(mockRequest, mockResponse));
    }
}