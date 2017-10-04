package org.adam.currency.command;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BindException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CurrencyCommandValidatorTest {

    @Mock
    private CurrencyService mockCurrencyService;

    private CurrencyCommandValidator validator;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        validator = new CurrencyCommandValidator(mockCurrencyService);
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
    }

    @Test
    void testSupports() throws Exception {
        assertEquals(true, validator.supports(CurrencyCommand.class));
    }

    @Test
    void shouldFailSupports() throws Exception {
        assertEquals(false, validator.supports(User.class));
    }

    @Test
    void testValidate() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void shouldRejectEmptyAmount() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.blank", errors.getFieldError("amount").getCode());
    }

    @Test
    void shouldRejectInvalidAmount() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount("abc");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.positive.numeric.value", errors.getFieldError("amount").getCode());
    }

    @Test
    void shouldRejectEmptyFromCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.select.currency", errors.getFieldError("from").getCode());
    }

    @Test
    void shouldRejectInvalidFromCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom("AAA");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.valid.currency", errors.getFieldError("from").getCode());
    }

    @Test
    void shouldRejectEmptyToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.select.currency", errors.getFieldError("to").getCode());
    }

    @Test
    void shouldRejectInvalidToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("AAA");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.valid.currency", errors.getFieldError("to").getCode());
    }

    @Test
    void shouldRejectSameFromAndToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("GBP");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("error.select.diff.currencies", errors.getFieldError("to").getCode());
    }

    private CurrencyCommand createCurrencyCommand() {
        CurrencyCommand command = new CurrencyCommand();
        command.setAmount("120.50");
        command.setFrom("GBP");
        command.setTo("EUR");
        return command;
    }
}