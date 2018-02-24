package org.adam.currency.command;

import org.adam.currency.domain.Country;
import org.adam.currency.helper.DateHelper;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCommandValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandValidator.class);
    private static final String ERROR_BLANK = "error.blank";

    private UserService userService;
    private CountryService countryService;

    public UserCommandValidator(UserService userService, CountryService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand) o;
        validateUserName(errors, command);
        validatePassword(errors, command);
        validateFirstAndLastName(errors);
        validateEmailAddress(errors, command);
        validateBirthDate(errors, command);
        validateAddress(errors, command);
    }

    private void validateBirthDate(Errors errors, UserCommand command) {
        if (StringUtils.isBlank(command.getBirthDate())) {
            errors.rejectValue("birthDate", ERROR_BLANK);
        } else if (!DateHelper.isCorrectDate(command.getBirthDate())) {
            errors.rejectValue("birthDate", "error.invalid.date.format", new Object[]{DateHelper.APPLICATION_DATE_FORMAT}, null);
        }
    }

    private void validateEmailAddress(Errors errors, UserCommand command) {
        if (StringUtils.isBlank(command.getEmail())) {
            errors.rejectValue("email", ERROR_BLANK);
        } else if (!EmailValidator.getInstance().isValid(command.getEmail())) {
            errors.rejectValue("email", "error.email.not.valid");
        }
    }

    private void validateFirstAndLastName(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", ERROR_BLANK);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", ERROR_BLANK);
    }

    private void validatePassword(Errors errors, UserCommand command) {
        if (StringUtils.isEmpty(command.getPassword())) {
            errors.rejectValue("password", ERROR_BLANK);
        } else if (command.getPassword().length() < 8) {
            errors.rejectValue("password", "error.password.too.short", new Object[]{"8"}, null);
        } else if (!command.getPassword().equals(command.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "error.repeated.password.different");
        }
    }

    private void validateUserName(Errors errors, UserCommand command) {
        if (StringUtils.isEmpty(command.getName())) {
            errors.rejectValue("name", ERROR_BLANK);
        } else if (userService.findUserByName(command.getName()) != null) {
            errors.rejectValue("name", "error.name.already.exists");
        }
    }

    private void validateAddress(Errors errors, UserCommand command) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", ERROR_BLANK);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", ERROR_BLANK);
        Country country = validateCountry(errors, command);
        validatePostCode(errors, country, command.getPostCode());

    }

    private Country validateCountry(Errors errors, UserCommand command) {
        if (StringUtils.isBlank(command.getCountry())) {
            errors.rejectValue("country", "error.select.value");
        } else {
            Country country = countryService.findByCode(command.getCountry());
            if (country == null) {
                errors.rejectValue("country", "error.select.value");
                LOGGER.error("Country with code:{} does not exist in COUNTRY table", command.getCountry());
            } else {
                return country;
            }
        }
        return null;
    }

    void validatePostCode(Errors errors, Country country, String postCode) {
        if (StringUtils.isBlank(postCode)) {
            errors.rejectValue("postCode", ERROR_BLANK);
            return;
        }
        if (country == null || StringUtils.isEmpty(country.getPostCodeRegExp())) {
            return;
        }
        Pattern pattern = Pattern.compile(country.getPostCodeRegExp());
        Matcher matcher = pattern.matcher(postCode.toUpperCase());
        if (!matcher.matches()) {
            errors.rejectValue("postCode", "error.invalid.postCode");
        }
    }
}
