package org.adam.currency.helper;

import org.adam.currency.domain.Currency;
import org.adam.currency.dto.CurrencyDTO;

import java.util.function.Function;

public class CurrencyTransformer implements Function<Currency, CurrencyDTO> {

    @Override
    public CurrencyDTO apply(Currency currency) {
        CurrencyDTO dto = new CurrencyDTO();
        if (currency == null) {
            return dto;
        }
        dto.setCode(currency.getCode());
        dto.setName(currency.getName());
        dto.setCountry(currency.getCountry().getName());
        return dto;
    }
}
