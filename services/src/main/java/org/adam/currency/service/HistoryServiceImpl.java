package org.adam.currency.service;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.repository.GenericRepository;
import org.adam.currency.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service("historyService")
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private SettingService settingService;

    @Override
    public History findBy(Currency currencyFrom, Currency currencyTo, LocalDate date) {
        return historyRepository.findBy(currencyFrom, currencyTo, date);
    }

    @Override
    public History findRecent(Currency currencyFrom, Currency currencyTo) {
        return historyRepository.findRecent(currencyFrom, currencyTo);
    }

    @Override
    public List<History> findByUser(User user) {
        int limit = settingService.getIntSetting(SettingField.HISTORY_SHOW_LAST);
        return historyRepository.findByUser(user, limit);
    }

    @Override
    public History saveHistory(User user, Currency currencyFrom, Currency currencyTo, Double amount, LocalDate date, CurrencyResponse currencyResponse, CallTypeEnum callType) {
        History history = new HistoryBuilder().withUser(user).withCurrencyFrom(currencyFrom).withCurrencyTo(currencyTo).withAmount(amount).withDate(date)
                .withRate(currencyResponse.getInfo().getQuote()).withResult(currencyResponse.getResult()).withTimeStamp(currencyResponse.getInfo().getTimestamp())
                .withCallType(callType).withCreateDate(LocalDateTime.now()).build();
        genericRepository.save(history);
        return history;
    }
}
