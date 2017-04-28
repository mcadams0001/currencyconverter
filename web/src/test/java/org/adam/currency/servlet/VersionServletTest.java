package org.adam.currency.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VersionServletTest {

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
    public void getVersion() throws Exception {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        servlet.doGet(mockRequest, mockResponse);
        verify(mockServletOutputStream).print("2.0.0");
    }

    @Test(expected = ServletException.class)
    public void throwServletException() throws Exception {
        when(servlet.getServletConfig()).thenReturn(mockServletConfig);
        when(mockServletConfig.getInitParameter(anyString())).thenReturn("2.0.0");
        when(mockResponse.getOutputStream()).thenReturn(mockServletOutputStream);
        doThrow(new IOException("failure")).when(mockServletOutputStream).print(anyString());
        servlet.doGet(mockRequest, mockResponse);
    }
}