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
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CurrencyResponseDTO{" +
                "success=" + success +
                ", quote=" + quote +
                ", result=" + result +
                '}';
    }
}
