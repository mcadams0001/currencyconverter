package org.adam.currency.service;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.fixture.CurrencyFixture;
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
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    public static final String RESPONSE = "{\n" +
            "  \"success\": true,\n" +
            "  \"terms\": \"https://currencylayer.com/terms\",\n" +
            "  \"privacy\": \"https://currencylayer.com/privacy\",\n" +
            "  \"query\": {\n" +
            "    \"from\": \"USD\",\n" +
            "    \"to\": \"GBP\",\n" +
            "    \"amount\": 10\n" +
            "  },\n" +
            "  \"info\": {\n" +
            "    \"timestamp\": 1454180070,\n" +
            "    \"quote\": 0.658443\n" +
            "  },\n" +
            "  \"result\": 6.58443\n" +
            "}\n";

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
    public void testConvertCurrency() throws Exception {
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", "120.0", LocalDate.of(2016, 1, 30));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        assertThat(currencyResponse.getQuote(), equalTo(0.658443));
        assertThat(currencyResponse.getTimestamp(), equalTo(LocalDateTime.of(2016, 1, 30, 18, 54, 30)));
        assertThat(currencyResponse.getResult(), equalTo(6.58443));
        assertThat(currencyResponse.isSuccess(), equalTo(true));
    }

    @Test
    public void shouldConvertCurrencyAndHandleError() throws Exception {
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(ERROR_RESPONSE);
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", "120.0", LocalDate.of(2016, 1, 30));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        assertThat(currencyResponse.getQuote(), nullValue());
        assertThat(currencyResponse.getTimestamp(), nullValue());
        assertThat(currencyResponse.getResult(), nullValue());
        assertThat(currencyResponse.isSuccess(), equalTo(false));
        assertThat(currencyResponse.getError(), equalTo("Your monthly usage limit has been reached. Please upgrade your subscription plan."));
    }

    @Test
    public void shouldCaptureExceptionOnServiceCall() throws Exception {
        Logger.getLogger(CurrencyServiceImpl.class).setLevel(Level.OFF);
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RestClientException("Call failure"));
        when(mockGenericRepository.findById(eq(Currency.class), eq("GBP"))).thenReturn(CurrencyFixture.GBP);
        when(mockGenericRepository.findById(eq(Currency.class), eq("EUR"))).thenReturn(CurrencyFixture.EUR);
        CurrencyResponseDTO currencyResponse = service.convertCurrency(user, "GBP", "EUR", "120.0", LocalDate.of(2016, 1, 30));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        verify(mockRestTemplate).getForObject(anyString(), eq(String.class));
        assertThat(currencyResponse.getError(), equalTo(CurrencyService.MSG_SERVICE_UNAVAILABLE));
    }


    @Test
    public void testGetUrl() throws Exception {
        when(mockSettingService.getSetting(SettingField.CURRENCY_SERVICE_URL)).thenReturn(SettingFixture.CURRENCY_SERVICE_URL.getValue());
        when(mockSettingService.getSetting(SettingField.ACCESS_KEY)).thenReturn(SettingFixture.ACCESS_KEY.getValue());
        String url = service.getUrl("GBP", "EUR", "120.0", LocalDate.of(2016, 1, 30));
        verify(mockSettingService).getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockSettingService).getSetting(SettingField.ACCESS_KEY);
        assertThat(url, equalTo("http://apilayer.net/api/currency?access_key=abcd&from=GBP&to=EUR&amount=120.0&date=2016-01-30"));

    }

    @Test
    public void testParseResponseAndCaptureException() throws Exception {
        Logger.getLogger(CurrencyServiceImpl.class).setLevel(Level.OFF);
        CurrencyResponse response = service.parseResponse("abc");
        assertThat(response.getError().getInfo(), equalTo(CurrencyService.MSG_INVALID_RESPONSE));

    }
}