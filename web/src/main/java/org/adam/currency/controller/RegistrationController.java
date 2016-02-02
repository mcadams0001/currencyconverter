package org.adam.currency.controller;

import org.adam.currency.command.UserCommand;
import org.adam.currency.command.UserCommandValidator;
import org.adam.currency.common.Constants;
import org.adam.currency.domain.User;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    public static final Logger LOGGER = Logger.getLogger(RegistrationController.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserService userService;

    @InitBinder("command")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserCommandValidator(userService, countryService));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject(Constants.Parameters.COMMAND, new UserCommand());
        mav.addObject(Constants.Parameters.COUNTRIES, countryService.findAll());
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute(value = "command") UserCommand command, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.put(Constants.Parameters.COUNTRIES, countryService.findAll());
            return "register";
        }
        User user = userService.createUser(command);
        modelMap.put(Constants.Parameters.USER, user);
        LOGGER.info("User: " + user.getName() + " has completed registration");
        return "registerSuccess";
    }
}
