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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl service = new HistoryServiceImpl();

    @Mock
    private HistoryRepository mockHistoryRepository;

    @Mock
    private GenericRepository mockGenericRepository;

    @Mock
    private SettingService mockSettingService;

    @Test
    public void testFindBy() throws Exception {
        History expectedHistory = new History();
        when(mockHistoryRepository.findBy(isA(Currency.class), isA(Currency.class), isA(LocalDate.class))).thenReturn(expectedHistory);
        History history = service.findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        verify(mockHistoryRepository).findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        assertThat(history, sameInstance(expectedHistory));

    }

    @Test
    public void testFindByUser() throws Exception {
        List<History> history = new ArrayList<>();
        history.add(new History());
        when(mockSettingService.getIntSetting(SettingField.HISTORY_SHOW_LAST)).thenReturn(10);
        when(mockHistoryRepository.findByUser(isA(User.class), anyInt())).thenReturn(history);
        List<History> historyList = service.findByUser(UserFixture.TEST_USER);
        verify(mockSettingService).getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockHistoryRepository).findByUser(UserFixture.TEST_USER, 10);
        assertThat(historyList, equalTo(history));
    }

    @Test
    public void shouldSaveHistory() throws Exception {
        User user = UserFixture.TEST_USER;
        LocalDate date = LocalDate.of(2016, 1, 30);
        CurrencyResponse response = CurrencyResponseFixture.SUCCESS_RESPONSE;
        Currency currencyFrom = CurrencyFixture.GBP;
        Currency currencyTo = CurrencyFixture.EUR;
        CallTypeEnum callType = CallTypeEnum.WEB_SERVICE;
        History history = service.saveHistory(user, currencyFrom, currencyTo, 200.0d, date, response, callType);
        verify(mockGenericRepository).save(history);
        assertThat(history.getCurrencyFrom(), equalTo(currencyFrom));
        assertThat(history.getCurrencyTo(), equalTo(currencyTo));
        assertThat(history.getAmount(), equalTo(200.0d));
        assertThat(history.getDate(), equalTo(date));
        assertThat(history.getRate(), equalTo(response.getInfo().getQuote()));
        assertThat(history.getResult(), equalTo(response.getResult()));
        assertThat(history.getTimeStamp(), equalTo(response.getInfo().getTimestamp()));
        assertThat(history.getCallType(), equalTo(callType));
    }

}