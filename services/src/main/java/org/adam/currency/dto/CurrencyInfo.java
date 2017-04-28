package org.adam.currency.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.adam.currency.helper.DoubleSerializer;
import org.adam.currency.helper.LocalDateTimeDeserializer;
import org.adam.currency.helper.LocalDateTimeSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class CurrencyInfo {
    @JsonSerialize(using = DoubleSerializer.class)
    private Double quote;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    public CurrencyInfo() {
    }

    public CurrencyInfo(Double quote, LocalDateTime timestamp) {
        this.quote = quote;
        this.timestamp = timestamp;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyInfo that = (CurrencyInfo) o;

        return new EqualsBuilder()
                .append(quote, that.quote)
                .append(timestamp, that.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(quote)
                .append(timestamp)
                .toHashCode();
    }
}
