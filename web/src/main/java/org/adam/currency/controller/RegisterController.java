package org.adam.currency.controller;

import org.adam.currency.command.UserCommand;
import org.adam.currency.command.UserRegistrationValidator;
import org.adam.currency.common.Constants;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.GenericService;
import org.adam.currency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject(Constants.Parameters.COMMAND, new UserCommand());
        mav.addObject(Constants.Parameters.COUNTRIES, countryService.findAll());
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute UserCommand command, BindException errors) {
        Validator validator = new UserRegistrationValidator(userService, countryService);
        validator.validate(command, errors);
        if(errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject(Constants.Parameters.COMMAND, command);
            mav.addObject(Constants.Parameters.COUNTRIES, countryService.findAll());
            return mav;
        }
        userService.createUser(command);
        return new ModelAndView("registerSuccess");
    }
}
