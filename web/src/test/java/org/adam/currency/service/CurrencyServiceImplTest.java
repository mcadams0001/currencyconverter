package org.adam.currency.service;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.HistoryFixture;
import org.adam.currency.fixture.SettingFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.repository.GenericRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.isA;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    public static final String RESPONSE = "{\n" +
            "  \"success\":true,\n" +
            "  \"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\n" +
            "  \"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\n" +
            "  \"timestamp\":1454180070,\n" +
            "  \"source\":\"USD\",\n" +
            "  \"quotes\":{\n" +
            "    \"USDEUR\":0.923318,\n" +
            "    \"USDGBP\":0.70205\n" +
            "  }\n" +
            "}";

    public static final String ERROR_RESPONSE = "{\n" +
            "  \"success\": false,\n" +
            "  \"error\": {\n" +
            "    \"code\": 104,\n" +
            "    \"info\": \"Your monthly usage limit has been reached. Please upgrade your subscription plan.\"    \n" +
            "  }\n" +
            "}\n";

    @InjectMocks
    private CurrencyServiceImpl service = new CurrencyServiceImpl();

    @Mock
    private GenericRepository mockGenericRepository;

    @Mock
    private SettingService mockSettingService;

    @Mock
    private HistoryService mockHistoryService;

    @Mock
    private RestTemplate mockRestTemplate;

    private User user = UserFixture.TEST_USER;

    @Before
    public void setUp() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Currency> currencies = CurrencyFixture.CURRENCIES;
        when(mockGenericRepository.findAll(Currency.class, "name")).thenReturn(currencies);
        List<Currency> actualList = service.findAll();
        verify(mockGenericRepository).findAll(Currency.class, "name");
        assertThat(actualList, sameInstance(currencies));
    }

    @Test
    public void testConvertCurrencyFromWebService() throws Exception {
        LocalDate asOfDate = LocalDate.now();
        Currency currencyFrom = CurrencyFixture.GBP;
        Currency currencyTo = CurrencyFixture.EUR;
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(currencyFrom);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(currencyTo);
        when(mockHistoryService.findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class))).thenReturn(null);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockHistoryService).findRecent(isA(Currency.class), isA(Currency.class));
        verify(mockHistoryService, never()).findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class));
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        verify(mockHistoryService).saveHistory(eq(user), eq(currencyFrom), eq(currencyTo), eq(120.0d), eq(asOfDate), isA(CurrencyResponse.class), eq(CallTypeEnum.WEB_SERVICE));
        assertThat(currencyResponse.getQuote(), equalTo(1.315174133));
        assertThat(currencyResponse.getTimestamp(), equalTo(LocalDateTime.of(2016, 1, 30, 18, 54, 30)));
        assertThat(currencyResponse.getResult(), equalTo(157.82089596));
        assertThat(currencyResponse.isSuccess(), equalTo(true));
        assertThat(currencyResponse.getAmount(), equalTo(120.0d));
        assertThat(currencyResponse.getCurrencyFrom().getCode(), equalTo("GBP"));
        assertThat(currencyResponse.getCurrencyTo().getCode(), equalTo("EUR"));
    }

    @Test
    public void shouldConvertCurrencyWithRecentQueryFromHistory() throws Exception {
        LocalDate asOfDate = LocalDate.now();
        Currency currencyFrom = CurrencyFixture.GBP;
        Currency currencyTo = CurrencyFixture.EUR;
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(currencyFrom);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(currencyTo);
        when(mockHistoryService.findRecent(isA(Currency.class), isA(Currency.class))).thenReturn(HistoryFixture.GBP_EUR_2016_1_30);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService, never()).getSetting(isA(SettingField.class));
        verify(mockHistoryService).findRecent(currencyFrom, currencyTo);
        verify(mockHistoryService, never()).findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class));
        verify(mockRestTemplate, never()).getForObject(anyString(), eq(String.class));
        verify(mockHistoryService).saveHistory(eq(user), eq(currencyFrom), eq(currencyTo), eq(120.0d), eq(asOfDate), isA(CurrencyResponse.class), eq(CallTypeEnum.DATABASE));
        assertThat(currencyResponse.getQuote(), equalTo(0.658443));
        assertThat(currencyResponse.getTimestamp(), equalTo(LocalDateTime.of(2016, 1, 30, 18, 54, 30)));
        assertThat(currencyResponse.getResult(), equalTo(79.01316));
        assertThat(currencyResponse.isSuccess(), equalTo(true));
        assertThat(currencyResponse.getAmount(), equalTo(120.0d));
        assertThat(currencyResponse.getCurrencyFrom().getCode(), equalTo("GBP"));
        assertThat(currencyResponse.getCurrencyTo().getCode(), equalTo("EUR"));
    }


    @Test
    public void testConvertCurrencyFromDataBase() throws Exception {
        LocalDate asOfDate = LocalDate.of(2016, 1, 30);
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        when(mockHistoryService.findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class))).thenReturn(HistoryFixture.GBP_EUR_2016_1_30);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockHistoryService).findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class));
        verify(mockHistoryService).saveHistory(eq(user), eq(CurrencyFixture.GBP), eq(CurrencyFixture.EUR), eq(120.0d), eq(asOfDate), isA(CurrencyResponse.class), eq(CallTypeEnum.DATABASE));
        verify(mockSettingService, never()).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService, never()).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate, never()).getForObject(anyString(), eq(String.class));
        assertThat(currencyResponse.getQuote(), equalTo(0.658443));
        assertThat(currencyResponse.getTimestamp(), equalTo(LocalDateTime.of(2016, 1, 30, 18, 54, 30)));
        assertThat(currencyResponse.getResult(), equalTo(79.01316));
        assertThat(currencyResponse.isSuccess(), equalTo(true));
    }

    @Test
    public void shouldConvertCurrencyAndHandleError() throws Exception {
        LocalDate asOfDate = LocalDate.now();
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(ERROR_RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        verify(mockHistoryService, never()).saveHistory(isA(User.class), isA(Currency.class), isA(Currency.class), anyDouble(), isA(LocalDate.class), isA(CurrencyResponse.class), isA(CallTypeEnum.class));
        assertThat(currencyResponse.getQuote(), nullValue());
        assertThat(currencyResponse.getTimestamp(), nullValue());
        assertThat(currencyResponse.getResult(), nullValue());
        assertThat(currencyResponse.isSuccess(), equalTo(false));
        assertThat(currencyResponse.getError(), equalTo("Your monthly usage limit has been reached. Please upgrade your subscription plan."));
    }

    @Test
    public void shouldCaptureExceptionOnServiceCall() throws Exception {
        LocalDate asOfDate = LocalDate.now();
        Logger.getLogger(CurrencyServiceImpl.class).setLevel(Level.OFF);
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RestClientException("Call failure"));
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        assertThat(currencyResponse.getError(), equalTo(CurrencyService.MSG_SERVICE_UNAVAILABLE));
    }


    @Test
    public void testGetUrl() throws Exception {
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        String url = service.getUrl("GBP", "EUR");
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        assertThat(url, equalTo("http://apilayer.net/api/live?access_key=abcd&format=1&source=USD&currencies=GBP,EUR"));

    }

    @Test
    public void testParseResponseAndCaptureException() throws Exception {
        Logger.getLogger(CurrencyServiceImpl.class).setLevel(Level.OFF);
        CurrencyResponse response = service.parseResponse("abc", "GBP", "EUR", 200.0d);
        assertThat(response.getError().getInfo(), equalTo(CurrencyService.MSG_INVALID_RESPONSE));
    }

    @Test
    public void shouldConvertCurrencyWhenNoneAreUSD() throws Exception {
        Map<String, Double> quotes = new HashMap<>();
        quotes.put("USDGBP",0.702050d);
        quotes.put("USDEUR",0.923318d);
        double quote = service.getQuote(quotes, "GBP", "EUR");
        assertThat(quote, equalTo(1.315174133));
    }

    @Test
    public void shouldConvertCurrencyWhenSourceIsUSD() throws Exception {
        Map<String, Double> quotes = new HashMap<>();
        quotes.put("USDGBP",0.702050d);
        quotes.put("USDEUR",0.923318d);
        double quote = service.getQuote(quotes, "USD", "EUR");
        assertThat(quote, equalTo(0.923318d));
    }

    @Test
    public void shouldConvertCurrencyWhenTargetIsUSD() throws Exception {
        Map<String, Double> quotes = new HashMap<>();
        quotes.put("USDGBP",0.702050d);
        quotes.put("USDEUR",0.923318d);
        double quote = service.getQuote(quotes, "EUR", "USD");
        assertThat(quote, equalTo(1/0.923318d));
    }



}