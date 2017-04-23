package org.adam.currency.helper;

import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.dto.CurrencyResponseDTO;

import java.util.function.Function;

public class ResponseTransformer implements Function<CurrencyResponse, CurrencyResponseDTO> {
    @Override
    public CurrencyResponseDTO apply(CurrencyResponse currencyResponse) {
        CurrencyResponseDTO dto = new CurrencyResponseDTO();
        if (currencyResponse == null) {
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
