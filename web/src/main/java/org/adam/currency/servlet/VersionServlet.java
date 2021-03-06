package org.adam.currency.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VersionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            resp.getOutputStream().print(getServletConfig().getInitParameter("version"));
        }catch (IOException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
}
