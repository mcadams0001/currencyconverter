package org.adam.currency.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.adam.currency.helper.BooleanSerializer;
import org.adam.currency.helper.DoubleSerializer;
import org.adam.currency.helper.LocalDateTimeStringSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class CurrencyResponseDTO {
    @JsonSerialize(using = BooleanSerializer.class)
    private boolean success;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double quote;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double result;
    @JsonSerialize(using = LocalDateTimeStringSerializer.class)
    private LocalDateTime timestamp;

    private String error;

    private CurrencyDTO currencyFrom;
    private CurrencyDTO currencyTo;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double amount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public CurrencyDTO getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(CurrencyDTO currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public CurrencyDTO getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(CurrencyDTO currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "CurrencyResponseDTO{" +
                "success=" + success +
                ", quote=" + quote +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CurrencyResponseDTO that = (CurrencyResponseDTO) o;

        return new EqualsBuilder()
                .append(success, that.success)
                .append(quote, that.quote)
                .append(result, that.result)
                .append(timestamp, that.timestamp)
                .append(error, that.error)
                .append(currencyFrom, that.currencyFrom)
                .append(currencyTo, that.currencyTo)
                .append(amount, that.amount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(success)
                .append(quote)
                .append(result)
                .append(timestamp)
                .append(error)
                .append(currencyFrom)
                .append(currencyTo)
                .append(amount)
                .toHashCode();
    }
}
