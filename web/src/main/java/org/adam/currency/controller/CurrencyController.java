package org.adam.currency.controller;

import org.adam.currency.command.CurrencyCommand;
import org.adam.currency.command.CurrencyCommandValidator;
import org.adam.currency.common.Constants;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.helper.HttpServletHelper;
import org.adam.currency.security.PrincipalHelper;
import org.adam.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView displayCurrencies(Principal principal) {
        ModelAndView mav = new ModelAndView(Constants.ViewName.INDEX);
        User user = PrincipalHelper.getUserFromPrincipal(principal);
        mav.addObject(Constants.Parameters.USER, user);
        mav.addObject(Constants.Parameters.CURRENCIES, currencyService.findAll());
        return mav;
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> convert(@ModelAttribute CurrencyCommand command, HttpServletRequest request) {
        HttpHeaders httpHeaders = HttpServletHelper.createJsonResponseHeaders(request);
        BindException errors = new BindException(command, "command");
        Validator validator = new CurrencyCommandValidator(currencyService);
        validator.validate(command, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        CurrencyResponse currencyResponse = currencyService.convertCurrency(command.getFrom(), command.getTo(), command.getAmount(), command.getDate());
        return new ResponseEntity<>(HttpServletHelper.jsonResponse(currencyResponse), httpHeaders, HttpStatus.OK);
    }
}
