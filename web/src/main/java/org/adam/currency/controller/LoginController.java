package org.adam.currency.controller;

import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    static final String ERROR_MESSAGE = "Invalid user or password";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLoginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView mav = new ModelAndView(ViewName.LOGIN.getName());
        if (error != null) {
            mav.addObject(Parameters.ERROR.getName(), ERROR_MESSAGE);
        }
        return mav;
    }
}
