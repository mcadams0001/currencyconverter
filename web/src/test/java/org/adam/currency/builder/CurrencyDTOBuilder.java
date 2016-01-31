package org.adam.currency.builder;

import org.adam.currency.dto.CurrencyResponseDTO;

import java.time.LocalDateTime;

public class CurrencyDTOBuilder {
    private boolean success;
    private Double quote;
    private Double result;
    private LocalDateTime timestamp;
    private String error;

    public CurrencyDTOBuilder withSuccess(boolean success){
        this.success = success;
        return this;
    }

    public CurrencyDTOBuilder withQuote(Double quote){
        this.quote = quote;
        return this;
    }

    public CurrencyDTOBuilder withResult(Double result){
        this.result = result;
        return this;
    }

    public CurrencyDTOBuilder withTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public CurrencyDTOBuilder withError(String error){
        this.error = error;
        return this;
    }

    public CurrencyResponseDTO build() {
        CurrencyResponseDTO dto = new CurrencyResponseDTO();
        dto.setSuccess(success);
        dto.setTimestamp(timestamp);
        dto.setQuote(quote);
        dto.setError(error);
        dto.setResult(result);
        return dto;
    }
}
