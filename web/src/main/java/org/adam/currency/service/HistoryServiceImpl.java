package org.adam.currency.service;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("historyService")
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public History findBy(Currency currencyFrom, Currency currencyTo, LocalDate date) {
        return historyRepository.findBy(currencyFrom, currencyTo, date);
    }

    @Override
    public List<History> findByUser(User user, int limit) {
        return historyRepository.findByUser(user, limit);
    }

    @Override
    public void saveHistory(Currency currencyFrom, Currency currencyTo, String amount, LocalDate date, CurrencyResponse currencyResponse, CallTypeEnum webService) {

    }
}
