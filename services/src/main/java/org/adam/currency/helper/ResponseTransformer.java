package org.adam.currency.helper;

import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.apache.commons.collections4.Transformer;

public class ResponseTransformer implements Transformer<CurrencyResponse, CurrencyResponseDTO> {
    @Override
    public CurrencyResponseDTO transform(CurrencyResponse currencyResponse) {
        CurrencyResponseDTO dto = new CurrencyResponseDTO();
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
