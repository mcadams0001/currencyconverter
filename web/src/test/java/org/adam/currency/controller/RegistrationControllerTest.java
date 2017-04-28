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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

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
    public void testShowForm() throws Exception {
        List<Country> countries = CountryFixture.COUNTRIES;
        when(mockCountryService.findAll()).thenReturn(countries);
        ModelAndView mav = controller.showForm();
        verify(mockCountryService).findAll();
        assertThat(mav, notNullValue());
        assertThat(mav.getModel().get(Parameters.COMMAND.getName()), equalTo(new UserCommand()));
        assertThat(mav.getModel().get(Parameters.COUNTRIES.getName()), equalTo(countries));
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        ModelMap modelMap = new ModelMap();
        UserCommand command = new UserCommand();
        User user = UserFixture.TEST_USER;
        when(mockUserService.createUser(command)).thenReturn(user);
        String viewName = controller.registerUser(command, mockBindResult, modelMap);
        verify(mockUserService).createUser(command);
        assertThat(viewName, equalTo("registerSuccess"));
        assertThat(modelMap, hasKey(Parameters.USER.getName()));
    }

    @Test
    public void shouldRegisterValidator() throws Exception {
        controller.initBinder(mockWebDataBinder);
        ArgumentCaptor<Validator> validatorArgumentCaptor = ArgumentCaptor.forClass(Validator.class);
        verify(mockWebDataBinder).addValidators(validatorArgumentCaptor.capture());
        Validator actualValidator = validatorArgumentCaptor.getValue();
        assertThat(actualValidator instanceof UserCommandValidator, equalTo(true));
    }


}