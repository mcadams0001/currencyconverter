package org.adam.currency.command;

import org.adam.currency.domain.Country;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandValidatorTest {

    private UserCommandValidator validator;

    @Mock
    private UserService mockUserService;

    @Mock
    private CountryService mockCountryService;

    @BeforeEach
    void setup() {
        validator = new UserCommandValidator(mockUserService, mockCountryService);
    }

    @Test
    void testSupports() {
        assertTrue(validator.supports(UserCommand.class));
    }

    @Test
    void shouldFailSupports() {
        assertFalse(validator.supports(User.class));
    }


    @Test
    void testValidatePass() {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertFalse(errors.hasErrors());
    }

    @Test
    void shouldRejectExistingUser() {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(UserFixture.TEST_USER);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "name", "error.name.already.exists");
    }

    @Test
    void shouldRejectEmptyUserName() {
        UserCommand command = createValidCommand();
        command.setName("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService, never()).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "name", "error.blank");
    }

    @Test
    void shouldRejectEmptyPassword() {
        UserCommand command = createValidCommand();
        command.setPassword("");
        command.setRepeatPassword("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertEquals(1, errors.getErrorCount());
        FieldError fieldError = errors.getFieldError("password");
        assertNotNull(fieldError);
        assertEquals("error.blank", fieldError.getCode());
    }

    @Test
    void shouldRejectShortPassword() {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefg");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "password", "error.password.too.short");
    }

    @Test
    void shouldRejectPasswordDifferentThanRepeated() {
        UserCommand command = createValidCommand();
        command.setPassword("abcdefgh");
        command.setRepeatPassword("abcdefg");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "repeatPassword", "error.repeated.password.different");
    }

    @Test
    void shouldRejectEmptyFirstAndLastName() {
        UserCommand command = createValidCommand();
        command.setFirstName("");
        command.setLastName("");
        BindException errors = new BindException(command, "command");
        when(mockUserService.findUserByName(anyString())).thenReturn(null);
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 2, "firstName", "error.blank");
        assertTrue(errors.hasFieldErrors("lastName"));
        FieldError lastNameFieldError = errors.getFieldError("lastName");
        assertNotNull(lastNameFieldError);
        assertEquals("error.blank", lastNameFieldError.getCode());
    }

    @Test
    void shouldRejectEmptyEmailAddress() {
        UserCommand command = createValidCommand();
        command.setEmail("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "email", "error.blank");
    }

    private void assertOneErrorFor(BindException errors, int i, String email, String s) {
        assertEquals(i, errors.getErrorCount());
        assertTrue(errors.hasFieldErrors(email));
        FieldError fieldError = errors.getFieldError(email);
        assertNotNull(fieldError);
        assertEquals(s, fieldError.getCode());
    }

    @Test
    void shouldRejectInvalidEmailAddress() {
        UserCommand command = createValidCommand();
        command.setEmail("a@a");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "email", "error.email.not.valid");
    }

    @Test
    void shouldRejectEmptyBirthDate() {
        UserCommand command = createValidCommand();
        command.setBirthDate("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "birthDate", "error.blank");
    }

    @Test
    void shouldRejectInvalidBirthDate() {
        UserCommand command = createValidCommand();
        command.setBirthDate("10-10-2999");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "birthDate", "error.invalid.date.format");
    }


    @Test
    void shouldRejectEmptyStreet() {
        UserCommand command = createValidCommand();
        command.setStreet("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "street", "error.blank");
    }

    @Test
    void shouldRejectEmptyCity() {
        UserCommand command = createValidCommand();
        command.setCity("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "city", "error.blank");
    }

    @Test
    void shouldRejectEmptyCountry() {
        UserCommand command = createValidCommand();
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "country", "error.select.value");
    }

    @Test
    void shouldRejectNonExistingCountryCode() {
        UserCommand command = createValidCommand();
        command.setCountry("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "country", "error.select.value");
    }

    @Test
    void shouldRejectEmptyPostCode() {
        UserCommand command = createValidCommand();
        command.setPostCode("");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "postCode", "error.blank");
    }

    @Test
    void shouldRejectInvalidPostCode() {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(CountryFixture.UK);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "postCode", "error.invalid.postCode");
    }

    @Test
    void shouldNotRejectPostCodeIfCountryIsEmpty() {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("");
        BindException errors = new BindException(command, "command");
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService, never()).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "country", "error.select.value");
    }

    @Test
    void shouldNotRejectPostCodeIfCountryCodeIsInvalid() {
        UserCommand command = createValidCommand();
        command.setPostCode("ABC");
        command.setCountry("RRR");
        BindException errors = new BindException(command, "command");
        when(mockCountryService.findByCode(anyString())).thenReturn(null);
        validator.validate(command, errors);
        verify(mockUserService).findUserByName(command.getName());
        verify(mockCountryService).findByCode(command.getCountry());
        assertOneErrorFor(errors, 1, "country", "error.select.value");
    }

    @Test
    void skipValidatePostCodeForNullCountry() {
        UserCommand command = createValidCommand();
        BindException errors = new BindException(command, "command");
        validator.validatePostCode(errors, null, "W7 TD1");
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void skipValidatePostCodeForEmptyRegExp() {
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