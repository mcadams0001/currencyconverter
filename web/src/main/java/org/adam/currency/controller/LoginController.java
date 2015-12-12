package org.adam.currency.controller;

import org.adam.currency.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLoginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView mav = new ModelAndView(Constants.ViewName.LOGIN);
        if(error != null) {
            mav.addObject("error", "Invalid user or password");
        }
        return mav;
    }
}
