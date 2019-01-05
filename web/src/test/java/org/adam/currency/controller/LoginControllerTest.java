package org.adam.currency.controller;

import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController controller = new LoginController();

    @Test
    void displayLoginPage() {
        ModelAndView mav = controller.displayLoginPage(null);
        assertNotNull(mav);
        assertEquals(ViewName.LOGIN.getName(), mav.getViewName());
        assertFalse(mav.getModel().containsKey(Parameters.ERROR.getName()));
    }

    @Test
    void displayLoginPageError() {
        ModelAndView mav = controller.displayLoginPage("error");
        assertNotNull(mav);
        assertEquals(ViewName.LOGIN.getName(), mav.getViewName());
        assertEquals(LoginController.ERROR_MESSAGE, mav.getModel().get(Parameters.ERROR.getName()));
    }
}