package org.adam.currency.controller;

import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LoginControllerTest {

    private LoginController controller = new LoginController();
    @Test
    public void displayLoginPage() throws Exception {
        ModelAndView mav = controller.displayLoginPage(null);
        assertThat(mav, notNullValue());
        assertThat(mav.getViewName(), equalTo(ViewName.LOGIN.getName()));
        assertThat(mav.getModel().containsKey(Parameters.ERROR.getName()), equalTo(false));
    }

    @Test
    public void displayLoginPageError() throws Exception {
        ModelAndView mav = controller.displayLoginPage("error");
        assertThat(mav, notNullValue());
        assertThat(mav.getViewName(), equalTo(ViewName.LOGIN.getName()));
        assertThat(mav.getModel().get(Parameters.ERROR.getName()), equalTo(LoginController.INVALID_USER_OR_PWD));
    }
}