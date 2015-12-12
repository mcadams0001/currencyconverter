package org.adam.currency.controller;

import org.adam.currency.command.UserCommand;
import org.adam.currency.common.Constants;
import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.service.GenericService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController controller = new RegisterController();

    @Mock
    private GenericService mockGenericService;

    @Test
    public void testShowForm() throws Exception {
        List<Country> countries = CountryFixture.COUNTRIES;
        when(mockGenericService.findAll(Country.class)).thenReturn(countries);
        ModelAndView mav = controller.showForm();
        verify(mockGenericService).findAll(Country.class);
        assertThat(mav, notNullValue());
        assertThat(mav.getModel().get(Constants.Parameters.COMMAND), equalTo(new UserCommand()));
        assertThat(mav.getModel().get(Constants.Parameters.COUNTRIES), equalTo(countries));
    }
}