package org.adam.currency.helper;

import org.adam.currency.domain.History;
import org.adam.currency.dto.HistoryDTO;
import org.apache.commons.collections4.Transformer;

public class HistoryTransformer implements Transformer<History, HistoryDTO> {
    @Override
    public HistoryDTO transform(History history) {
        HistoryDTO dto = new HistoryDTO();
        if (history == null) {
            return dto;
        }
        CurrencyTransformer currencyTransformer = new CurrencyTransformer();
        dto.setCurrencyFrom(currencyTransformer.transform(history.getCurrencyFrom()));
        dto.setCurrencyTo(currencyTransformer.transform(history.getCurrencyTo()));
        dto.setResult(history.getResult());
        dto.setRate(history.getRate());
        dto.setAmount(history.getAmount());
        dto.setTimeStamp(history.getTimeStamp());
        dto.setDate(history.getDate());
        dto.setId(history.getId());
        return dto;
    }
}
