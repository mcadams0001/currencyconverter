package org.adam.currency.controller;

import org.adam.currency.command.UserCommand;
import org.adam.currency.command.UserCommandValidator;
import org.adam.currency.common.Parameters;
import org.adam.currency.domain.Country;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.service.CountryService;
import org.adam.currency.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController controller = new RegistrationController();

    @Mock
    private UserService mockUserService;

    @Mock
    private CountryService mockCountryService;

    @Mock
    private WebDataBinder mockWebDataBinder;

    @Mock
    private BindingResult mockBindResult;

    @Test
    void testShowForm() {
        List<Country> countries = CountryFixture.COUNTRIES;
        when(mockCountryService.findAll()).thenReturn(countries);
        ModelAndView mav = controller.showForm();
        verify(mockCountryService).findAll();
        assertNotNull(mav);
        assertEquals(new UserCommand(), mav.getModel().get(Parameters.COMMAND.getName()));
        assertEquals(countries, mav.getModel().get(Parameters.COUNTRIES.getName()));
    }

    @Test
    void shouldRegisterUser() {
        ModelMap modelMap = new ModelMap();
        UserCommand command = new UserCommand();
        User user = UserFixture.TEST_USER;
        when(mockUserService.createUser(command)).thenReturn(user);
        String viewName = controller.registerUser(command, mockBindResult, modelMap);
        verify(mockUserService).createUser(command);
        assertEquals("registerSuccess", viewName);
        assertTrue(modelMap.containsKey(Parameters.USER.getName()));
    }

    @Test
    void shouldNotRegisterUser() {
        ModelMap modelMap = new ModelMap();
        UserCommand command = new UserCommand();
        when(mockBindResult.hasErrors()).thenReturn(true);
        String viewName = controller.registerUser(command, mockBindResult, modelMap);
        verify(mockUserService, never()).createUser(command);
        assertEquals("register", viewName);
        assertTrue(modelMap.containsKey(Parameters.COUNTRIES.getName()));
    }

    @Test
    void shouldRegisterValidator() {
        controller.initBinder(mockWebDataBinder);
        ArgumentCaptor<Validator> validatorArgumentCaptor = ArgumentCaptor.forClass(Validator.class);
        verify(mockWebDataBinder).addValidators(validatorArgumentCaptor.capture());
        Validator actualValidator = validatorArgumentCaptor.getValue();
        assertTrue(actualValidator instanceof UserCommandValidator);
    }


}