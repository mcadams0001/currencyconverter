package org.adam.currency.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.adam.currency.helper.DoubleSerializer;
import org.adam.currency.helper.LocalDateTimeDeserializer;
import org.adam.currency.helper.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class CurrencyInfo {
    @JsonSerialize(using = DoubleSerializer.class)
    private Double quote;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

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
}
