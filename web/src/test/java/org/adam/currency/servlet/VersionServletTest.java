package org.adam.currency.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    @Test
    void getVersion() throws IOException, ServletException {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        servlet.doGet(mockRequest, mockResponse);
        verify(mockServletOutputStream).print("2.0.0");
    }

    @Test
    void throwServletException() throws IOException {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        doThrow(new IOException("failure")).when(mockServletOutputStream).print(anyString());
        assertThrows(ServletException.class, () -> servlet.doGet(mockRequest, mockResponse));
    }
}