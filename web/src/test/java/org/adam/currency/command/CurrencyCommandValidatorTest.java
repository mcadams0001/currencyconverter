package org.adam.currency.command;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.service.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyCommandValidatorTest {

    @Mock
    private CurrencyService mockCurrencyService;

    private CurrencyCommandValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new CurrencyCommandValidator(mockCurrencyService);
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
    }

    @Test
    public void testSupports() throws Exception {
        assertThat(validator.supports(CurrencyCommand.class), equalTo(true));
    }

    @Test
    public void shouldFailSupports() throws Exception {
        assertThat(validator.supports(User.class), equalTo(false));
    }

    @Test
    public void testValidate() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(0));
    }

    @Test
    public void shouldRejectEmptyAmount() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("amount").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectInvalidAmount() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setAmount("abc");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("amount").getCode(), equalTo("error.positive.numeric.value"));
    }

    @Test
    public void shouldRejectEmptyFromCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("from").getCode(), equalTo("error.select.currency"));
    }

    @Test
    public void shouldRejectInvalidFromCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setFrom("AAA");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("from").getCode(), equalTo("error.valid.currency"));
    }

    @Test
    public void shouldRejectEmptyToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo(null);
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("to").getCode(), equalTo("error.select.currency"));
    }

    @Test
    public void shouldRejectInvalidToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("AAA");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("to").getCode(), equalTo("error.valid.currency"));
    }

    @Test
    public void shouldRejectSameFromAndToCurrency() throws Exception {
        CurrencyCommand command = createCurrencyCommand();
        command.setTo("GBP");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.getFieldError("to").getCode(), equalTo("error.select.diff.currencies"));
    }

    private CurrencyCommand createCurrencyCommand() {
        CurrencyCommand command = new CurrencyCommand();
        command.setAmount("120.50");
        command.setFrom("GBP");
        command.setTo("EUR");
        return command;
    }
}