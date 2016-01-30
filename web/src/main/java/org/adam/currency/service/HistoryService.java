package org.adam.currency.service;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {
    History findBy(Currency currencyFrom, Currency currencyTo, LocalDate date);
    List<History> findByUser(User user, int limit);
    void saveHistory(Currency currencyFrom, Currency currencyTo, String amount, LocalDate date, CurrencyResponse currencyResponse, CallTypeEnum webService);
}
