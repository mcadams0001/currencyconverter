package org.adam.currency.command;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandValidatorTest {

    private UserCommandValidator validator;

    @Mock
    private UserService mockUserService;

    @Mock
    private CountryService mockCountryService;

    @Before
    public void beforeTest() throws Exception {
        validator = new UserCommandValidator(mockUserService, mockCountryService);
    }

    @Test
    public void testSupports() throws Exception {
        assertThat(validator.supports(UserCommand.class), equalTo(true));
    }

    @Test
    public void shouldFailSupports() throws Exception {
        assertThat(validator.supports(User.class), equalTo(false));
    }


    @Test
    public void testValidatePass() throws Exception {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.hasErrors(), equalTo(false));
    }

    @Test
    public void shouldRejectExistingUser() throws Exception {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(UserFixture.TEST_USER);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("name"), equalTo(true));
        assertThat(errors.getFieldError("name").getCode(), equalTo("error.name.already.exists"));
    }

    @Test
    public void shouldRejectEmptyUserName() throws Exception {
        UserCommand command = createValidCommand();
        command.setName("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService, never()).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("name"), equalTo(true));
        assertThat(errors.getFieldError("name").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectEmptyPassword() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("");
        command.setRepeatPassword("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("password"), equalTo(true));
        assertThat(errors.getFieldError("password").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectShortPassword() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefg");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("password"), equalTo(true));
        assertThat(errors.getFieldError("password").getCode(), equalTo("error.password.too.short"));
    }

    @Test
    public void shouldRejectPasswordDifferentThanRepeated() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefgh");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("repeatPassword"), equalTo(true));
        assertThat(errors.getFieldError("repeatPassword").getCode(), equalTo("error.repeated.password.different"));
    }

    @Test
    public void shouldRejectEmptyFirstAndLastName() throws Exception {
        UserCommand command = createValidCommand();
        command.setFirstName("");
        command.setLastName("");
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(null);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(2));
        assertThat(errors.hasFieldErrors("firstName"), equalTo(true));
        assertThat(errors.getFieldError("firstName").getCode(), equalTo("error.blank"));
        assertThat(errors.hasFieldErrors("lastName"), equalTo(true));
        assertThat(errors.getFieldError("lastName").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectEmptyEmailAddress() throws Exception {
        UserCommand command = createValidCommand();
        command.setEmail("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("email"), equalTo(true));
        assertThat(errors.getFieldError("email").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectInvalidEmailAddress() throws Exception {
        UserCommand command = createValidCommand();
        command.setEmail("a@a");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("email"), equalTo(true));
        assertThat(errors.getFieldError("email").getCode(), equalTo("error.email.not.valid"));
    }

    @Test
    public void shouldRejectEmptyBirthDate() throws Exception {
        UserCommand command = createValidCommand();
        command.setBirthDate("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("birthDate"), equalTo(true));
        assertThat(errors.getFieldError("birthDate").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectInvalidBirthDate() throws Exception {
        UserCommand command = createValidCommand();
        command.setBirthDate("10-10-2999");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("birthDate"), equalTo(true));
        assertThat(errors.getFieldError("birthDate").getCode(), equalTo("error.invalid.date.format"));
    }


    @Test
    public void shouldRejectEmptyStreet() throws Exception {
        UserCommand command = createValidCommand();
        command.setStreet("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("street"), equalTo(true));
        assertThat(errors.getFieldError("street").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectEmptyCity() throws Exception {
        UserCommand command = createValidCommand();
        command.setCity("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("city"), equalTo(true));
        assertThat(errors.getFieldError("city").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectEmptyCountry() throws Exception {
        UserCommand command = createValidCommand();
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("country"), equalTo(true));
        assertThat(errors.getFieldError("country").getCode(), equalTo("error.select.value"));
    }

    @Test
    public void shouldRejectNonExistingCountryCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setCountry("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("country"), equalTo(true));
        assertThat(errors.getFieldError("country").getCode(), equalTo("error.select.value"));
    }

    @Test
    public void shouldRejectEmptyPostCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("postCode"), equalTo(true));
        assertThat(errors.getFieldError("postCode").getCode(), equalTo("error.blank"));
    }

    @Test
    public void shouldRejectInvalidPostCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("postCode"), equalTo(true));
        assertThat(errors.getFieldError("postCode").getCode(), equalTo("error.invalid.postCode"));
    }

    @Test
    public void shouldNotRejectPostCodeIfCountryIsEmpty() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("country"), equalTo(true));
        assertThat(errors.getFieldError("country").getCode(), equalTo("error.select.value"));
    }

    @Test
    public void shouldNotRejectPostCodeIfCountryCodeIsInvalid() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("RRR");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertThat(errors.getErrorCount(), equalTo(1));
        assertThat(errors.hasFieldErrors("country"), equalTo(true));
        assertThat(errors.getFieldError("country").getCode(), equalTo("error.select.value"));
    }

    private UserCommand createValidCommand() {
        UserCommand command = new UserCommand();
        User user = UserFixture.TEST_USER;
        command.setName(user.getName());
        command.setPassword(user.getPassword());
        command.setRepeatPassword(user.getPassword());
        command.setEmail(user.getEmailAddress());
        command.setFirstName(user.getFirstName());
        command.setLastName(user.getLastName());
        command.setBirthDate("01-Apr-1981");
        command.setCity(user.getAddress().getCity());
        command.setStreet(user.getAddress().getStreet());
        command.setPostCode(user.getAddress().getPostCode());
        command.setCountry(user.getAddress().getCountry().getCode());
        return command;
    }
}