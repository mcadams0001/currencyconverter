package org.adam.currency.helper;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestValidatorHelper {

    public static <T> void assertOneErrorMessage(Set<ConstraintViolation<T>> violations, String message) {
        List<ConstraintViolation<T>> list = new ArrayList<>(violations);
        if (list.size() > 1) {
            list.forEach(e -> System.out.println(e.getMessage()));
        }
        assertFalse(list.isEmpty(), "No errors were reported");
        assertEquals(1, list.size(), "More than one error was found");
        assertEquals(message, list.get(0).getMessage(), "Different error was found");
    }

    public static <T> void assertNoErrors(Set<ConstraintViolation<T>> violations) {
        List<ConstraintViolation<T>> list = new ArrayList<>(violations);
        if (list.size() > 0) {
            list.forEach(e -> System.out.println(e.getMessage()));
        }
        assertEquals(0, list.size(), "Errors were found");
    }

    public static <T> BindingResult toBindingResult(Set<ConstraintViolation<T>> violations, BindingResult bindingResult) {
        for (ConstraintViolation<T> violation : violations) {
            bindingResult.addError(new FieldError("command", violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return bindingResult;
    }

    public static <T> void validateAndAddToBindingResult(T command, BindingResult bindingResult) {
        Set<ConstraintViolation<T>> violationSet = validateAndReturnViolations(command);
        toBindingResult(violationSet, bindingResult);
    }

    public static <T> Set<ConstraintViolation<T>> validateAndReturnViolations(T command) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(command);
    }

}
