package org.adam.currency.controller;

import org.adam.currency.builder.CurrencyDTOBuilder;
import org.adam.currency.command.CurrencyCommand;
import org.adam.currency.common.Parameters;
import org.adam.currency.common.ViewName;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.HistoryDTO;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.HistoryFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.helper.AuthenticationHelper;
import org.adam.currency.helper.HttpServletHelper;
import org.adam.currency.service.CurrencyService;
import org.adam.currency.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

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

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "httpServletHelper", new HttpServletHelper());
    }

    @Test
    void testDisplayCurrencies() {
        AuthenticationHelper.setupPrincipalForUserDetails(user, mockAuthentication);
        ModelAndView mav = controller.displayCurrencies(mockAuthentication);
        assertNotNull(mav);
        assertEquals(ViewName.INDEX.getName(), mav.getViewName());
        UserDTO actual = (UserDTO) mav.getModel().get(Parameters.USER.getName());
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
    }

    @Test
    void shouldGetCurrencies() {
        List<Currency> currencies = CurrencyFixture.CURRENCIES;
        when(mockCurrencyService.findAll()).thenReturn(currencies);
        ResponseEntity<String> responseEntity = controller.displayForm(mockRequest);
        verify(mockCurrencyService).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{" + SEPARATOR + "  \"viewName\" : \"currencyForm\"," + SEPARATOR + "  \"currencies\" : [ {" + SEPARATOR + "    \"code\" : \"EUR\"," + SEPARATOR + "    \"name\" : \"Euro\"," + SEPARATOR + "    \"country\" : \"Germany\"" + SEPARATOR + "  }, {" + SEPARATOR + "    \"code\" : \"USD\"," + SEPARATOR + "    \"name\" : \"US Dollar\"," + SEPARATOR + "    \"country\" : \"United States\"" + SEPARATOR + "  }, {" + SEPARATOR + "    \"code\" : \"GBP\"," + SEPARATOR + "    \"name\" : \"British Pound\"," + SEPARATOR + "    \"country\" : \"United Kingdom\"" + SEPARATOR + "  } ]" + SEPARATOR + "}", responseEntity.getBody());
    }

    @Test
    void testConvert() {
        AuthenticationHelper.setupPrincipalForUserDetails(user, mockAuthentication);
        CurrencyCommand command = new CurrencyCommand();
        command.setFrom("GBP");
        command.setTo("EUR");
        command.setAmount("125.50");
        command.setDate(LocalDate.of(2016, 1, 30));
        CurrencyResponseDTO response = new CurrencyDTOBuilder().withSuccess(true).withResult(125.50d).withQuote(0.7d).withTimestamp(LocalDateTime.of(2016, 1, 30, 19, 14, 30)).build();
        when(mockCurrencyService.findAll()).thenReturn(CurrencyFixture.CURRENCIES);
        when(mockCurrencyService.convertCurrency(any(User.class), anyString(), anyString(), anyDouble(), any())).thenReturn(response);
        ResponseEntity<String> responseEntity = controller.convert(mockAuthentication, command, mockRequest);
        verify(mockCurrencyService).convertCurrency(user, command.getFrom(), command.getTo(), Double.parseDouble(command.getAmount()), Optional.of(command.getDate()));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\r\n  \"success\" : \"true\",\r\n  \"quote\" : \"0.7\",\r\n  \"result\" : \"125.5\",\r\n  \"timestamp\" : \"30-Jan-2016 19:14:30\",\r\n  \"error\" : null,\r\n  \"currencyFrom\" : null,\r\n  \"currencyTo\" : null,\r\n  \"amount\" : null\r\n}".replace("\r\n", SEPARATOR), responseEntity.getBody());
    }

    @Test
    void testNotConvert() {
        AuthenticationHelper.setupPrincipalForUserDetails(user, mockAuthentication);
        CurrencyController spyController = spy(controller);
        CurrencyCommand command = new CurrencyCommand();
        command.setFrom("GBP");
        command.setTo("EUR");
        command.setAmount("125.50");
        command.setDate(LocalDate.of(2016, 1, 30));
        doReturn(true).when(spyController).isCurrencyInvalid(any());
        ResponseEntity<String> responseEntity = spyController.convert(mockAuthentication, command, mockRequest);
        verify(mockCurrencyService, never()).convertCurrency(user, command.getFrom(), command.getTo(), Double.parseDouble(command.getAmount()), Optional.of(command.getDate()));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void shouldGetHistory() {
        AuthenticationHelper.setupPrincipalForUserDetails(user, mockAuthentication);
        when(mockHistoryService.findByUser(any(User.class))).thenReturn(Collections.singletonList(HistoryFixture.GBP_EUR_2016_1_30));
        ResponseEntity<String> responseEntity = controller.displayHistory(mockAuthentication, mockRequest);
        verify(mockHistoryService).findByUser(user);
        assertEquals("{\r\n  \"viewName\" : \"currencyHistory\",\r\n  \"historyList\" : [ {\r\n    \"id\" : null,\r\n    \"currencyFrom\" : {\r\n      \"code\" : \"GBP\",\r\n      \"name\" : \"British Pound\",\r\n      \"country\" : \"United Kingdom\"\r\n    },\r\n    \"currencyTo\" : {\r\n      \"code\" : \"EUR\",\r\n      \"name\" : \"Euro\",\r\n      \"country\" : \"Germany\"\r\n    },\r\n    \"date\" : \"30-Jan-2016\",\r\n    \"rate\" : \"0.658443\",\r\n    \"timeStamp\" : \"30-Jan-2016 18:54:30\",\r\n    \"result\" : \"300.0\",\r\n    \"amount\" : \"200.0\"\r\n  } ]\r\n}".replace("\r\n", SEPARATOR), responseEntity.getBody());
    }

    @Test
    void shouldGetHistoryView() {
        assertEquals("currencyHistoryEmpty", controller.getHistoryView(new ArrayList<>()));
        assertEquals("currencyHistory", controller.getHistoryView(Collections.singletonList(new HistoryDTO())));
    }

}