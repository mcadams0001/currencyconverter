package org.adam.currency.command;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyCommandValidatorTest {

    @Mock
    private CurrencyService mockCurrencyService;

    private CurrencyCommandValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CurrencyCommandValidator(mockCurrencyService);
    }

    @Test
    void testSupports() {
        assertTrue(validator.supports(CurrencyCommand.class));
    }

    @Test
    void shouldFailSupports() {
        assertFalse(validator.supports(User.class));
    }

    @Test
    void testValidate() {
        CurrencyCommand command = createCurrencyCommand();
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void shouldRejectEmptyAmount() {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount(null);
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError amountError = errors.getFieldError("amount");
        assertNotNull(amountError);
        assertEquals("error.blank", amountError.getCode());
    }

    @Test
    void shouldRejectInvalidAmount() {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount("abc");
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError amountError = errors.getFieldError("amount");
        assertNotNull(amountError);
        assertEquals("error.positive.numeric.value", amountError.getCode());
    }

    @Test
    void shouldRejectEmptyFromCurrency() {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError fromError = errors.getFieldError("from");
        assertNotNull(fromError);
        assertEquals("error.select.currency", fromError.getCode());
    }

    @Test
    void shouldRejectInvalidFromCurrency() {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom("AAA");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError fromError = errors.getFieldError("from");
        assertNotNull(fromError);
        assertEquals("error.valid.currency", fromError.getCode());
    }

    @Test
    void shouldRejectEmptyToCurrency() {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo(null);
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError toError = errors.getFieldError("to");
        assertNotNull(toError);
        assertEquals("error.select.currency", toError.getCode());
    }

    @Test
    void shouldRejectInvalidToCurrency() {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("AAA");
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError toError = errors.getFieldError("to");
        assertNotNull(toError);
        assertEquals("error.valid.currency", toError.getCode());
    }

    @Test
    void shouldRejectSameFromAndToCurrency() {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("GBP");
        BindException errors = new BindException(command, "command");
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        FieldError toError = errors.getFieldError("to");
        assertNotNull(toError);
        assertEquals("error.select.diff.currencies", toError.getCode());
    }

    private CurrencyCommand createCurrencyCommand() {
        CurrencyCommand command = new CurrencyCommand();
        command.setAmount("120.50");
        command.setFrom("GBP");
        command.setTo("EUR");
        return command;
    }
}