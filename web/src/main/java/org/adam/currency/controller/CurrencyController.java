package org.adam.currency.controller;

import org.adam.currency.common.Constants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CurrencyController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayCurrencies() {
        return new ModelAndView(Constants.ViewName.INDEX);
    }
}
