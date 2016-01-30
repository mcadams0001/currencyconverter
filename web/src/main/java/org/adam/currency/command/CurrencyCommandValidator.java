package org.adam.currency.command;

import org.adam.currency.domain.Currency;
import org.adam.currency.service.CurrencyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Currency command validator.
 */
public class CurrencyCommandValidator implements Validator {

    private CurrencyService currencyService;

    public CurrencyCommandValidator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CurrencyCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CurrencyCommand command = (CurrencyCommand) o;
        List<Currency> currencies = currencyService.findAll();
        Map<String, Currency> map = currencies.stream().collect(toMap(Currency::getCode, c -> c));
        validateAmount(command.getAmount(), errors);
        if (validateCurrency(map, command.getFrom(), errors, "from") && validateCurrency(map, command.getTo(), errors, "to") && command.getFrom().equalsIgnoreCase(command.getTo())) {
            errors.rejectValue("to", "error.select.diff.currencies");
        }
    }

    private void validateAmount(String amount, Errors errors) {
        if (StringUtils.isBlank(amount)) {
            errors.rejectValue("amount", "error.blank");
        } else if (!isDoubleNumber(amount)) {
            errors.rejectValue("amount", "error.positive.numeric.value");
        }
    }

    private boolean validateCurrency(Map<String, Currency> map, String currency, Errors errors, String attributeName) {
        boolean valid = true;
        if (StringUtils.isBlank(currency)) {
            errors.rejectValue(attributeName, "error.select.currency");
            valid = false;
        } else if (!map.containsKey(currency)) {
            errors.rejectValue(attributeName, "error.valid.currency");
            valid = false;
        }
        return valid;
    }

    private boolean isDoubleNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
