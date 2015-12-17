package org.adam.currency.command;

import org.adam.currency.domain.Country;
import org.adam.currency.helper.DateHelper;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCommandValidator implements Validator {
    public static final Logger LOGGER = Logger.getLogger(UserCommandValidator.class);

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
        if(StringUtils.isBlank(command.getBirthDate())) {
            errors.rejectValue("birthDate", "error.blank");
        } else if(!DateHelper.isCorrectDate(command.getBirthDate())) {
            errors.rejectValue("birthDate", "error.invalid.date.format", new Object[]{DateHelper.APPLICATION_DATE_FORMAT}, null);
        }
    }

    private void validateEmailAddress(Errors errors, UserCommand command) {
        if (StringUtils.isBlank(command.getEmail())) {
            errors.rejectValue("email", "error.blank");
        } else if (!EmailValidator.getInstance().isValid(command.getEmail())) {
            errors.rejectValue("email", "error.email.not.valid");
        }
    }

    private void validateFirstAndLastName(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.blank");
    }

    private void validatePassword(Errors errors, UserCommand command) {
        if (StringUtils.isEmpty(command.getPassword())) {
            errors.rejectValue("password", "error.blank");
        } else if(command.getPassword().length() < 8) {
            errors.rejectValue("password", "error.password.too.short", new Object[]{"8"}, null);
        } else if (!command.getPassword().equals(command.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "error.repeated.password.different");
        }
    }

    private void validateUserName(Errors errors, UserCommand command) {
        if (StringUtils.isEmpty(command.getName())) {
            errors.rejectValue("name", "error.blank");
        } else if (userService.findUserByName(command.getName()) != null) {
            errors.rejectValue("name", "error.name.already.exists");
        }
    }

    private void validateAddress(Errors errors, UserCommand command) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "error.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.blank");
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
                LOGGER.error("Country with code:" + command.getCountry() + " does not exist in COUNTRY table");
            } else {
                return country;
            }
        }
        return null;
    }

    private void validatePostCode(Errors errors, Country country, String postCode) {
        if (StringUtils.isBlank(postCode)) {
            errors.rejectValue("postCode", "error.blank");
            return;
        }
        if (country == null || StringUtils.isEmpty(country.getPostCodeRegExp())) {
            return;
        }
        Pattern pattern = Pattern.compile(country.getPostCodeRegExp());
        Matcher matcher = pattern.matcher(postCode);
        if (!matcher.matches()) {
            errors.rejectValue("postCode", "error.invalid.postCode");
        }
    }
}
