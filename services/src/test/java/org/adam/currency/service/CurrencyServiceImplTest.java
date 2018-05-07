package org.adam.currency.service;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.HistoryFixture;
import org.adam.currency.fixture.SettingFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.repository.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class CurrencyServiceImplTest {

    private static final String RESPONSE = "{\n" +
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

    private static final String ERROR_RESPONSE = "{\n" +
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

    @BeforeEach
    void setUp() {
        initMocks(this);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        ReflectionTestUtils.setField(service, "accessKey", "abcd");
    }

    @Test
    void testFindAll() {
        List<Currency> currencies = CurrencyFixture.CURRENCIES;
        when(mockGenericRepository.findAll(Currency.class, "name")).thenReturn(currencies);
        List<Currency> actualList = service.findAll();
        verify(mockGenericRepository).findAll(Currency.class, "name");
        assertSame(currencies, actualList);
    }

    @Test
    void testConvertCurrencyFromWebService() {
        LocalDate asOfDate = LocalDate.now();
        Currency currencyFrom = CurrencyFixture.GBP;
        Currency currencyTo = CurrencyFixture.EUR;
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(currencyFrom);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(currencyTo);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockHistoryService).findRecent(isA(Currency.class), isA(Currency.class));
        verify(mockHistoryService, never()).findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class));
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        verify(mockHistoryService).saveHistory(eq(user), eq(currencyFrom), eq(currencyTo), eq(120.0d), eq(asOfDate), isA(CurrencyResponse.class), eq(CallTypeEnum.WEB_SERVICE));
        assertEquals(1.315174133, currencyResponse.getQuote(), 0.0001);
        assertEquals(LocalDateTime.of(2016, 1, 30, 18, 54, 30), currencyResponse.getTimestamp());
        assertEquals(157.82089596, currencyResponse.getResult(), 0.0001);
        assertTrue(currencyResponse.isSuccess());
        assertEquals(120.0d, currencyResponse.getAmount(), 0.1);
        assertEquals("GBP", currencyResponse.getCurrencyFrom().getCode());
        assertEquals("EUR", currencyResponse.getCurrencyTo().getCode());
    }

    @Test
    void shouldConvertCurrencyWithRecentQueryFromHistory() {
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
        assertEquals(0.658443, currencyResponse.getQuote(), 0.000001);
        assertEquals(LocalDateTime.of(2016, 1, 30, 18, 54, 30), currencyResponse.getTimestamp());
        assertEquals(79.01316, currencyResponse.getResult(), 0.000001);
        assertTrue(currencyResponse.isSuccess());
        assertEquals(120.0d, currencyResponse.getAmount(), 0.000001);
        assertEquals("GBP", currencyResponse.getCurrencyFrom().getCode());
        assertEquals("EUR", currencyResponse.getCurrencyTo().getCode());
    }


    @Test
    void testConvertCurrencyFromDataBase() {
        LocalDate asOfDate = LocalDate.of(2016, 1, 30);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        when(mockHistoryService.findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class))).thenReturn(HistoryFixture.GBP_EUR_2016_1_30);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockHistoryService).findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class));
        verify(mockHistoryService).saveHistory(eq(user), eq(CurrencyFixture.GBP), eq(CurrencyFixture.EUR), eq(120.0d), eq(asOfDate), isA(CurrencyResponse.class), eq(CallTypeEnum.DATABASE));
        verify(mockSettingService, never()).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockRestTemplate, never()).getForObject(anyString(), eq(String.class));
        assertEquals(0.658443, currencyResponse.getQuote(), 0.000001);
        assertEquals(LocalDateTime.of(2016, 1, 30, 18, 54, 30), currencyResponse.getTimestamp());
        assertEquals(79.01316, currencyResponse.getResult(), 0.000001);
        assertTrue(currencyResponse.isSuccess());
    }

    @Test
    void shouldConvertCurrencyAndHandleError() {
        LocalDate asOfDate = LocalDate.now();
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(ERROR_RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        verify(mockHistoryService, never()).saveHistory(isA(User.class), isA(Currency.class), isA(Currency.class), anyDouble(), isA(LocalDate.class), isA(CurrencyResponse.class), isA(CallTypeEnum.class));
        assertNull(currencyResponse.getQuote());
        assertNull(currencyResponse.getTimestamp());
        assertNull(currencyResponse.getResult());
        assertFalse(currencyResponse.isSuccess());
        assertEquals("Your monthly usage limit has been reached. Please upgrade your subscription plan.", currencyResponse.getError());
    }

    @Test
    void shouldCaptureExceptionOnServiceCall() {
        LocalDate asOfDate = LocalDate.now();
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RestClientException("Call failure"));
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", 120.0d, Optional.of(asOfDate));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        assertEquals(CurrencyServiceImpl.MSG_SERVICE_UNAVAILABLE, currencyResponse.getError());
    }


    @Test
    void testGetUrl() {
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        String url = service.getUrl("GBP", "EUR");
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        assertEquals("http://apilayer.net/api/live?access_key=abcd&format=1&source=USD&currencies=GBP,EUR", url);

    }

    @Test
    void testParseResponseAndCaptureException() {
        CurrencyResponse response = service.parseResponse("abc", "GBP", "EUR", 200.0d);
        assertEquals(CurrencyServiceImpl.MSG_INVALID_RESPONSE, response.getError().getInfo());
    }

    @Test
    void shouldConvertCurrencyWhenNoneAreUSD() {
        Map<String, Double> quotes = Map.of("USDGBP",0.702050d, "USDEUR", 0.923318d);
        double quote = service.getQuote(quotes, "GBP", "EUR");
        assertEquals(1.315174133, quote);
    }

    @Test
    void shouldConvertCurrencyWhenSourceIsUSD() {
        Map<String, Double> quotes = Map.of("USDGBP",0.702050d, "USDEUR", 0.923318d);
        double quote = service.getQuote(quotes, "USD", "EUR");
        assertEquals(0.923318d, quote);
    }

    @Test
    void shouldConvertCurrencyWhenTargetIsUSD() {
        Map<String, Double> quotes = Map.of("USDGBP",0.702050d, "USDEUR", 0.923318d);
        double quote = service.getQuote(quotes, "EUR", "USD");
        assertEquals(1 / 0.923318d, quote);
    }

    @Test
    void shouldNotConfertIfBothAreUSD() {
        Map<String, Double> quotes = Map.of("USDGBP",0.702050d, "USDEUR", 0.923318d);
        double quote = service.getQuote(quotes, "USD", "USD");
        assertEquals(1.0d, quote);
    }

    @Test
    void shouldConvertEURtoUSD() {
        Map<String, Double> quotes = Map.of("USDGBP",0.702050d, "USDEUR", 0.923318d);
        double quote = service.getQuote(quotes, "EUR", "USD");
        assertEquals(1.083050476650515d, quote);
    }

    @Test
    void getErrorIfHistoryIsNotAvailable() {
        CurrencyResponse response = service.getResultForPastDaysFromDatabase(user, 100.0d, LocalDate.of(2016, 4, 30), CurrencyFixture.GBP, CurrencyFixture.EUR);
        assertNotNull(response);
        assertNotNull(response.getError());
        assertEquals("Historical exchange rate for GBP and EUR for 30-Apr-2016 is not available", response.getError().getInfo());
    }

    @Test
    void returnResponseWithoutSavingHistory() {
        CurrencyServiceImpl spyService = spy(service);
        doReturn(null).when(spyService).invokeService(anyString(), anyString(), anyDouble());
        CurrencyResponse response = spyService.getResultsFromWebService(user, 200d, CurrencyFixture.GBP, CurrencyFixture.EUR);
        verify(mockHistoryService, never()).saveHistory(any(), any(), any(), anyDouble(), any(), any(), any());
        assertNull(response);
    }
}