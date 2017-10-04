package org.adam.currency.command;

import org.adam.currency.domain.Country;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserCommandValidatorTest {

    private UserCommandValidator validator;

    @Mock
    private UserService mockUserService;

    @Mock
    private CountryService mockCountryService;

    @BeforeEach
    void setup() throws Exception {
        initMocks(this);
        validator = new UserCommandValidator(mockUserService, mockCountryService);
    }

    @Test
    void testSupports() throws Exception {
        assertEquals(true, validator.supports(UserCommand.class));
    }

    @Test
    void shouldFailSupports() throws Exception {
        assertEquals(false, validator.supports(User.class));
    }


    @Test
    void testValidatePass() throws Exception {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(false, errors.hasErrors());
    }

    @Test
    void shouldRejectExistingUser() throws Exception {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(UserFixture.TEST_USER);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("name"));
        assertEquals("error.name.already.exists", errors.getFieldError("name").getCode());
    }

    @Test
    void shouldRejectEmptyUserName() throws Exception {
        UserCommand command = createValidCommand();
        command.setName("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService, never()).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("name"));
        assertEquals("error.blank", errors.getFieldError("name").getCode());
    }

    @Test
    void shouldRejectEmptyPassword() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("");
        command.setRepeatPassword("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("password"));
        assertEquals("error.blank", errors.getFieldError("password").getCode());
    }

    @Test
    void shouldRejectShortPassword() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefg");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("password"));
        assertEquals("error.password.too.short", errors.getFieldError("password").getCode());
    }

    @Test
    void shouldRejectPasswordDifferentThanRepeated() throws Exception {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefgh");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("repeatPassword"));
        assertEquals("error.repeated.password.different", errors.getFieldError("repeatPassword").getCode());
    }

    @Test
    void shouldRejectEmptyFirstAndLastName() throws Exception {
        UserCommand command = createValidCommand();
        command.setFirstName("");
        command.setLastName("");
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(null);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(2, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("firstName"));
        assertEquals("error.blank", errors.getFieldError("firstName").getCode());
        assertEquals(true, errors.hasFieldErrors("lastName"));
        assertEquals("error.blank", errors.getFieldError("lastName").getCode());
    }

    @Test
    void shouldRejectEmptyEmailAddress() throws Exception {
        UserCommand command = createValidCommand();
        command.setEmail("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("email"));
        assertEquals("error.blank", errors.getFieldError("email").getCode());
    }

    @Test
    void shouldRejectInvalidEmailAddress() throws Exception {
        UserCommand command = createValidCommand();
        command.setEmail("a@a");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("email"));
        assertEquals("error.email.not.valid", errors.getFieldError("email").getCode());
    }

    @Test
    void shouldRejectEmptyBirthDate() throws Exception {
        UserCommand command = createValidCommand();
        command.setBirthDate("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("birthDate"));
        assertEquals("error.blank", errors.getFieldError("birthDate").getCode());
    }

    @Test
    void shouldRejectInvalidBirthDate() throws Exception {
        UserCommand command = createValidCommand();
        command.setBirthDate("10-10-2999");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("birthDate"));
        assertEquals("error.invalid.date.format", errors.getFieldError("birthDate").getCode());
    }


    @Test
    void shouldRejectEmptyStreet() throws Exception {
        UserCommand command = createValidCommand();
        command.setStreet("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("street"));
        assertEquals("error.blank", errors.getFieldError("street").getCode());
    }

    @Test
    void shouldRejectEmptyCity() throws Exception {
        UserCommand command = createValidCommand();
        command.setCity("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("city"));
        assertEquals("error.blank", errors.getFieldError("city").getCode());
    }

    @Test
    void shouldRejectEmptyCountry() throws Exception {
        UserCommand command = createValidCommand();
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("country"));
        assertEquals("error.select.value", errors.getFieldError("country").getCode());
    }

    @Test
    void shouldRejectNonExistingCountryCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setCountry("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("country"));
        assertEquals("error.select.value", errors.getFieldError("country").getCode());
    }

    @Test
    void shouldRejectEmptyPostCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("postCode"));
        assertEquals("error.blank", errors.getFieldError("postCode").getCode());
    }

    @Test
    void shouldRejectInvalidPostCode() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("postCode"));
        assertEquals("error.invalid.postCode", errors.getFieldError("postCode").getCode());
    }

    @Test
    void shouldNotRejectPostCodeIfCountryIsEmpty() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("country"));
        assertEquals("error.select.value", errors.getFieldError("country").getCode());
    }

    @Test
    void shouldNotRejectPostCodeIfCountryCodeIsInvalid() throws Exception {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("RRR");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        assertEquals(true, errors.hasFieldErrors("country"));
        assertEquals("error.select.value", errors.getFieldError("country").getCode());
    }

    @Test
    void skipValidatePostCodeForNullCountry() throws Exception {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        validator.validatePostCode(errors, null, "W7 TD1");
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void skipValidatePostCodeForEmptyRegExp() throws Exception {
        UserCommand command = createValidCommand();
        Country country = new Country("FR", "France", null);
        BindException errors = new BindException(command, "command");
        validator.validatePostCode(errors, country, "W7 TD1");
        assertEquals(0, errors.getErrorCount());
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