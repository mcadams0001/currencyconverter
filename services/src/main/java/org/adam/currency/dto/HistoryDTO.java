package org.adam.currency.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.adam.currency.helper.DoubleSerializer;
import org.adam.currency.helper.LocalDateSerializer;
import org.adam.currency.helper.LocalDateTimeStringSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoryDTO {
    private Long id;
    private CurrencyDTO currencyFrom;
    private CurrencyDTO currencyTo;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double rate;
    @JsonSerialize(using = LocalDateTimeStringSerializer.class)
    private LocalDateTime timeStamp;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double result;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
