package org.adam.currency.controller;

import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class LoginControllerTest {

    private LoginController controller = new LoginController();

    @Test
     void displayLoginPage() throws Exception {
        ModelAndView mav = controller.displayLoginPage(null);
        assertNotNull(mav);
        assertEquals(ViewName.LOGIN.getName(), mav.getViewName());
        assertEquals(false, mav.getModel().containsKey(Parameters.ERROR.getName()));
    }

    @Test
     void displayLoginPageError() throws Exception {
        ModelAndView mav = controller.displayLoginPage("error");
        assertNotNull(mav);
        assertEquals(ViewName.LOGIN.getName(), mav.getViewName());
        assertEquals(LoginController.ERROR_MESSAGE, mav.getModel().get(Parameters.ERROR.getName()));
    }
}