package org.adam.currency.helper;

import org.adam.currency.dto.CurrencyDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.apache.commons.collections4.Transformer;

public class ResponseTransformer implements Transformer<CurrencyResponse, CurrencyDTO> {
    @Override
    public CurrencyDTO transform(CurrencyResponse currencyResponse) {
        CurrencyDTO dto = new CurrencyDTO();
        if(currencyResponse == null) {
            return dto;
        }
        dto.setResult(currencyResponse.getResult());
        dto.setQuote(currencyResponse.getInfo().getQuote());
        dto.setTimestamp(currencyResponse.getInfo().getTimestamp());
        dto.setSuccess(currencyResponse.getSuccess());
        dto.setError(currencyResponse.getError().getInfo());
        return dto;
    }
}
