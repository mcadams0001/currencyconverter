package org.adam.currency.controller;

import org.adam.currency.command.CurrencyCommand;
import org.adam.currency.command.CurrencyCommandValidator;
import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.HistoryDTO;
import org.adam.currency.helper.CurrencyTransformer;
import org.adam.currency.helper.HistoryTransformer;
import org.adam.currency.helper.HttpServletHelper;
import org.adam.currency.helper.UserTransformer;
import org.adam.currency.security.PrincipalHelper;
import org.adam.currency.service.CurrencyService;
import org.adam.currency.service.HistoryService;
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
import java.util.*;

import static java.util.stream.Collectors.toList;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView displayCurrencies(Principal principal) {
        ModelAndView mav = new ModelAndView(ViewName.INDEX.toString());
        User user = PrincipalHelper.getUserFromPrincipal(principal);
        mav.addObject(Parameters.USER.toString(), new UserTransformer().apply(user));
        return mav;
    }

    @RequestMapping(value = "/currency", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> displayForm(HttpServletRequest request) {
        HttpHeaders httpHeaders = HttpServletHelper.createJsonResponseHeaders(request);
        Map<String, Object> model = new HashMap<>();
        model.put(Parameters.CURRENCIES.getName(), currencyService.findAll().stream().map(new CurrencyTransformer()).collect(toList()));
        model.put(Parameters.VIEW_NAME.getName(), "currencyForm");
        return new ResponseEntity<>(HttpServletHelper.jsonResponse(model), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> convert(Principal principal, @ModelAttribute CurrencyCommand command, HttpServletRequest request) {
        HttpHeaders httpHeaders = HttpServletHelper.createJsonResponseHeaders(request);
        User user = PrincipalHelper.getUserFromPrincipal(principal);
        BindException errors = new BindException(command, "command");
        Validator validator = new CurrencyCommandValidator(currencyService);
        validator.validate(command, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        CurrencyResponseDTO currencyResponse = currencyService.convertCurrency(user, command.getFrom(), command.getTo(), Double.parseDouble(command.getAmount()), Optional.ofNullable(command.getDate()));
        return new ResponseEntity<>(HttpServletHelper.jsonResponse(currencyResponse), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<String> displayHistory(Principal principal, HttpServletRequest request) {
        HttpHeaders httpHeaders = HttpServletHelper.createJsonResponseHeaders(request);
        User user = PrincipalHelper.getUserFromPrincipal(principal);
        List<History> historyList = historyService.findByUser(user);
        Map<String, Object> model = new HashMap<>();
        Collection<HistoryDTO> historyDTOCollection = historyList.stream().map(new HistoryTransformer()).collect(toList());
        model.put(Parameters.HISTORY.getName(), historyDTOCollection);
        model.put(Parameters.VIEW_NAME.getName(), getHistoryView(historyDTOCollection));
        return new ResponseEntity<>(HttpServletHelper.jsonResponse(model), httpHeaders, HttpStatus.OK);
    }

    String getHistoryView(Collection<HistoryDTO> historyDTOCollection) {
        return !historyDTOCollection.isEmpty() ? "currencyHistory" : "currencyHistoryEmpty";
    }
}
