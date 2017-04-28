package org.adam.currency.builder;

import org.adam.currency.dto.CurrencyError;
import org.adam.currency.dto.CurrencyInfo;
import org.adam.currency.dto.CurrencyResponse;

import java.time.LocalDateTime;

public class CurrencyResponseBuilder {
    private Boolean success = Boolean.TRUE;
    private Double quote;
    private Double result;
    private LocalDateTime timestamp;
    private String errorMessage;
    private String errorCode;

    public CurrencyResponseBuilder withQuote(Double quote) {
        this.quote = quote;
        return this;
    }

    public CurrencyResponseBuilder withResult(Double result) {
        this.result = result;
        return this;
    }

    public CurrencyResponseBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public CurrencyResponseBuilder withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public CurrencyResponseBuilder withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public CurrencyResponseBuilder withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CurrencyResponse build() {
        CurrencyResponse response = new CurrencyResponse();
        response.setInfo(new CurrencyInfo(quote, timestamp));
        response.setResult(result);
        response.setSuccess(success);
        response.setError(new CurrencyError(errorCode, errorMessage));
        return response;
    }

}
