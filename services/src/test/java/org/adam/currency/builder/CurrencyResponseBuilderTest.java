package org.adam.currency.builder;

import org.adam.currency.dto.CurrencyError;
import org.adam.currency.dto.CurrencyResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CurrencyResponseBuilderTest {
    @Test
    void createResponse() throws Exception {
        CurrencyError error = new CurrencyError("404", "Access Denied");
        CurrencyResponse currencyResponse = new CurrencyResponse(1.12, 120.0d , LocalDateTime.of(2017,4,28,21,58,0));
        currencyResponse.setError(error);
        currencyResponse.setSuccess(false);
        CurrencyResponse actual = new CurrencyResponseBuilder()
                .withQuote(currencyResponse.getInfo().getQuote())
                .withResult(currencyResponse.getResult())
                .withSuccess(currencyResponse.getSuccess())
                .withTimestamp(currencyResponse.getInfo().getTimestamp())
                .withErrorCode(error.getCode())
                .withErrorMessage(error.getInfo())
                .build();
        assertEquals(currencyResponse, actual);
    }
}