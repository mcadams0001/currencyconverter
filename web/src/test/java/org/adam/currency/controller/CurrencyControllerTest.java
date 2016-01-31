package org.adam.currency.controller;

import org.adam.currency.builder.CurrencyDTOBuilder;
import org.adam.currency.command.CurrencyCommand;
import org.adam.currency.common.Constants;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.HistoryDTO;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.HistoryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.security.UserDetailsImpl;
import org.adam.currency.service.CurrencyService;
import org.adam.currency.service.HistoryService;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
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

    @Mock
    private HistoryService mockHistoryService;

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
        assertThat(responseEntity.getBody(), equalTo("{" + SEPARATOR + "  \"viewName\" : \"currencyForm\"," + SEPARATOR + "  \"currencies\" : [ {" + SEPARATOR + "    \"code\" : \"EUR\"," + SEPARATOR + "    \"name\" : \"Euro\"," + SEPARATOR + "    \"country\" : \"Germany\"" + SEPARATOR + "  }, {" + SEPARATOR + "    \"code\" : \"USD\"," + SEPARATOR + "    \"name\" : \"US Dollar\"," + SEPARATOR + "    \"country\" : \"United States\"" + SEPARATOR + "  }, {" + SEPARATOR + "    \"code\" : \"GBP\"," + SEPARATOR + "    \"name\" : \"British Pound\"," + SEPARATOR + "    \"country\" : \"United Kingdom\"" + SEPARATOR + "  } ]" + SEPARATOR + "}"));
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
        when(mockCurrencyService.convertCurrency(isA(User.class), anyString(), anyString(), anyDouble(), isA(Optional.class))).thenReturn(response);
        ResponseEntity<String> responseEntity = controller.convert(mockAuthentication, command, mockRequest);
        verify(mockCurrencyService).convertCurrency(user, command.getFrom(), command.getTo(), Double.parseDouble(command.getAmount()), Optional.of(command.getDate()));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(responseEntity.getBody(), equalTo("{" + SEPARATOR + "  \"success\" : \"true\"," + SEPARATOR + "  \"quote\" : \"0.7\"," + SEPARATOR + "  \"result\" : \"125.5\"," + SEPARATOR + "  \"timestamp\" : \"30-Jan-2016 19:14:30\"," + SEPARATOR + "  \"error\" : null" + SEPARATOR + "}"));
    }

    @Test
    public void shouldGetHistory() throws Exception {
        when(mockHistoryService.findByUser(isA(User.class))).thenReturn(Collections.singletonList(HistoryFixture.GBP_EUR_2016_1_30));
        ResponseEntity<String> responseEntity = controller.displayHistory(mockAuthentication, mockRequest);
        verify(mockHistoryService).findByUser(user);
        assertThat(responseEntity.getBody(), equalTo("{\r\n  \"viewName\" : \"currencyHistory\",\r\n  \"history\" : [ {\r\n    \"id\" : null,\r\n    \"currencyFrom\" : {\r\n      \"code\" : \"GBP\",\r\n      \"name\" : \"British Pound\",\r\n      \"country\" : \"United Kingdom\"\r\n    },\r\n    \"currencyTo\" : {\r\n      \"code\" : \"EUR\",\r\n      \"name\" : \"Euro\",\r\n      \"country\" : \"Germany\"\r\n    },\r\n    \"date\" : \"30-Jan-2016\",\r\n    \"rate\" : \"0.658443\",\r\n    \"timeStamp\" : \"30-Jan-2016 18:54:30\",\r\n    \"result\" : \"300.0\",\r\n    \"amount\" : \"200.0\"\r\n  } ]\r\n}".replace("\r\n", SEPARATOR)));
    }

    @Test
    public void shouldGetHistoryView() throws Exception {
        assertThat(controller.getHistoryView(new ArrayList<>()), equalTo("currencyHistoryEmpty"));
        assertThat(controller.getHistoryView(Collections.singletonList(new HistoryDTO())), equalTo("currencyHistory"));
    }

}