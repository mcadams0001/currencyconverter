package org.adam.currency.controller;

import org.adam.currency.builder.CurrencyDTOBuilder;
import org.adam.currency.command.CurrencyCommand;
import org.adam.currency.common.Constants;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.security.UserDetailsImpl;
import org.adam.currency.service.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    @InjectMocks
    private CurrencyController controller = new CurrencyController();

    @Mock
    private CurrencyService mockCurrencyService;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private HttpServletRequest mockRequest;

    private User user = UserFixture.TEST_USER;

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Before
    public void setUp() throws Exception {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        when(mockAuthentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    public void testDisplayCurrencies() throws Exception {
        ModelAndView mav = controller.displayCurrencies(mockAuthentication);
        assertThat(mav, notNullValue());
        assertThat(mav.getViewName(), equalTo(Constants.ViewName.INDEX));
        UserDTO actual = (UserDTO) mav.getModel().get(Constants.Parameters.USER);
        assertThat(actual.getFirstName(), equalTo(user.getFirstName()));
        assertThat(actual.getLastName(), equalTo(user.getLastName()));
    }

    @Test
    public void shouldGetCurrencies() throws Exception {
        List<Currency> currencies = CurrencyFixture.CURRENCIES;
        when(mockCurrencyService.findAll()).thenReturn(currencies);
        ResponseEntity<String> responseEntity = controller.displayForm(mockRequest);
        verify(mockCurrencyService).findAll();
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(responseEntity.getBody(), equalTo("{\r\n  \"currencies\" : [ {\r\n    \"code\" : \"EUR\",\r\n    \"name\" : \"Euro\",\r\n    \"country\" : \"Germany\"\r\n  }, {\r\n    \"code\" : \"USD\",\r\n    \"name\" : \"US Dollar\",\r\n    \"country\" : \"United States\"\r\n  }, {\r\n    \"code\" : \"GBP\",\r\n    \"name\" : \"British Pound\",\r\n    \"country\" : \"United Kingdom\"\r\n  } ]\r\n}"));
    }


    @Test
    public void testConvert() throws Exception {
        CurrencyCommand command = new CurrencyCommand();
        command.setFrom("GBP");
        command.setTo("EUR");
        command.setAmount("125.50");
        command.setDate(LocalDate.of(2016, 1, 30));
        CurrencyResponseDTO response = new CurrencyDTOBuilder().withSuccess(true).withResult(125.50d).withQuote(0.7d).withTimestamp(LocalDateTime.of(2016, 1, 30, 19, 14, 30)).build();
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        when(mockCurrencyService.convertCurrency(isA(User.class), anyString(), anyString(), anyString(), isA(LocalDate.class))).thenReturn(response);
        ResponseEntity<String> responseEntity = controller.convert(mockAuthentication, command, mockRequest);
        verify(mockCurrencyService).convertCurrency(user, command.getFrom(), command.getTo(), command.getAmount(), command.getDate());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(responseEntity.getBody(), equalTo("{" + SEPARATOR + "  \"success\" : \"true\"," + SEPARATOR + "  \"quote\" : \"0.7\"," + SEPARATOR + "  \"result\" : \"125.5\"," + SEPARATOR + "  \"timestamp\" : \"30-Jan-2016 19:14:30\"," + SEPARATOR + "  \"error\" : null" + SEPARATOR + "}"));
    }
}