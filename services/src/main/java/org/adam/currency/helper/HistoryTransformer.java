package org.adam.currency.helper;

import org.adam.currency.domain.History;
import org.adam.currency.dto.HistoryDTO;

import java.util.function.Function;

public class HistoryTransformer implements Function<History, HistoryDTO> {
    @Override
    public HistoryDTO apply(History history) {
        HistoryDTO dto = new HistoryDTO();
        if (history == null) {
            return dto;
        }
        CurrencyTransformer currencyTransformer = new CurrencyTransformer();
        dto.setCurrencyFrom(currencyTransformer.apply(history.getCurrencyFrom()));
        dto.setCurrencyTo(currencyTransformer.apply(history.getCurrencyTo()));
        dto.setResult(history.getResult());
        dto.setRate(history.getRate());
        dto.setAmount(history.getAmount());
        dto.setTimeStamp(history.getTimeStamp());
        dto.setDate(history.getDate());
        dto.setId(history.getId());
        return dto;
    }
}
