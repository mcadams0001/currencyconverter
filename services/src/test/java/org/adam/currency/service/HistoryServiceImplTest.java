package org.adam.currency.service;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.CurrencyResponseFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.repository.GenericRepository;
import org.adam.currency.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl service = new HistoryServiceImpl();

    @Mock
    private HistoryRepository mockHistoryRepository;

    @Mock
    private GenericRepository mockGenericRepository;

    @Mock
    private SettingService mockSettingService;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testFindBy() {
        History expectedHistory = new History();
        when(mockHistoryRepository.findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class))).thenReturn(expectedHistory);
        History history = service.findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        verify(mockHistoryRepository).findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        assertSame(expectedHistory, history);
    }

    @Test
    void shouldFindRecent() {
        History expectedHistory = new History();
        when(mockHistoryRepository.findRecent(isA(Currency.class), isA(Currency.class))).thenReturn(expectedHistory);
        History history = service.findRecent(CurrencyFixture.GBP, CurrencyFixture.EUR);
        verify(mockHistoryRepository).findRecent(CurrencyFixture.GBP, CurrencyFixture.EUR);
        assertSame(expectedHistory, history);
    }


    @Test
    void testFindByUser() {
        List<History> history = new ArrayList<>();
        history.add(new History());
        when(mockSettingService.getIntSetting(SettingField.HISTORY_SHOW_LAST)).thenReturn(10);
        when(mockHistoryRepository.findByUser(isA(User.class), anyInt())).thenReturn(history);
        List<History> historyList = service.findByUser(UserFixture.TEST_USER);
        verify(mockSettingService).getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockHistoryRepository).findByUser(UserFixture.TEST_USER, 10);
        assertEquals(history, historyList);
    }

    @Test
    void shouldSaveHistory() {
        User user = UserFixture.TEST_USER;
        LocalDate date = LocalDate.of(2016, 1, 30);
        CurrencyResponse response = CurrencyResponseFixture.SUCCESS_RESPONSE;
        Currency currencyFrom = CurrencyFixture.GBP;
        Currency currencyTo = CurrencyFixture.EUR;
        CallTypeEnum callType = CallTypeEnum.WEB_SERVICE;
        History history = service.saveHistory(user, currencyFrom, currencyTo, 200.0d, date, response, callType);
        verify(mockGenericRepository).save(history);
        assertEquals(currencyFrom, history.getCurrencyFrom());
        assertEquals(currencyTo, history.getCurrencyTo());
        assertEquals(200.0d, history.getAmount(), 0.1);
        assertEquals(date, history.getDate());
        assertEquals(response.getInfo().getQuote(), history.getRate());
        assertEquals(response.getResult(), history.getResult());
        assertEquals(response.getInfo().getTimestamp(), history.getTimeStamp());
        assertEquals(callType, history.getCallType());
        assertNotNull(history.getCreateDate());
    }

}