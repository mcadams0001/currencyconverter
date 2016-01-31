package org.adam.currency.helper;

import org.adam.currency.domain.Currency;
import org.adam.currency.dto.CurrencyDTO;
import org.apache.commons.collections4.Transformer;

public class CurrencyTransformer implements Transformer<Currency, CurrencyDTO> {

    @Override
    public CurrencyDTO transform(Currency currency) {
        CurrencyDTO dto = new CurrencyDTO();
        if(currency == null) {
            return dto;
        }
        dto.setCode(currency.getCode());
        dto.setName(currency.getName());
        dto.setCountry(currency.getCountry().getName());
        return dto;
    }
}
