package org.adam.currency.builder;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoryBuilder {
    private Currency currencyFrom;
    private Currency currencyTo;
    private User user;
    private Double amount;
    private LocalDate date;
    private Double rate;
    private Double result;
    private LocalDateTime createDate;
    private LocalDateTime timeStamp;
    private CallTypeEnum callType;

    public HistoryBuilder withCallType(CallTypeEnum callType){
        this.callType = callType;
        return this;
    }


    public HistoryBuilder withTimeStamp(LocalDateTime timestamp){
        this.timeStamp = timestamp;
        return this;
    }


    public HistoryBuilder withCurrencyFrom(Currency currencyFrom){
        this.currencyFrom = currencyFrom;
        return this;
    }

    public HistoryBuilder withCurrencyTo(Currency currencyTo){
        this.currencyTo = currencyTo;
        return this;
    }

    public HistoryBuilder withUser(User user){
        this.user = user;
        return this;
    }

    public HistoryBuilder withAmount(Double amount){
        this.amount = amount;
        return this;
    }

    public HistoryBuilder withDate(LocalDate date){
        this.date = date;
        return this;
    }

    public HistoryBuilder withRate(Double rate){
        this.rate = rate;
        return this;
    }

    public HistoryBuilder withResult(Double result){
        this.result = result;
        return this;
    }

    public HistoryBuilder withCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
        return this;
    }


    public History build() {
        History history = new History();
        history.setAmount(amount);
        history.setCurrencyFrom(currencyFrom);
        history.setCurrencyTo(currencyTo);
        history.setDate(date);
        history.setRate(rate);
        history.setResult(result);
        history.setUser(user);
        history.setCreateDate(createDate);
        history.setTimeStamp(timeStamp);
        history.setCallType(callType);
        return history;
    }

}
