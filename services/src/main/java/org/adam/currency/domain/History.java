package org.adam.currency.domain;

import org.adam.currency.common.CallTypeEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORY")
public class History {
    @Id
    @SequenceGenerator(name = "HISTORY_ID_GEN", sequenceName = "HISTORY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "HISTORY_ID_GEN")
    @Column(name = "HISTORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE_FROM", referencedColumnName = "CURRENCY_CODE")
    private Currency currencyFrom;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE_TO", referencedColumnName = "CURRENCY_CODE")
    private Currency currencyTo;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "EXCHANGE_DATE")
    private LocalDate date;

    @Column(name = "EXCHANGE_RATE")
    private Double rate;

    @Column(name = "EXCHANGE_TIME_STAMP")
    private LocalDateTime timeStamp;

    @Column(name = "RESULT")
    private Double result;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CALL_TYPE")
    @Enumerated(EnumType.STRING)
    private CallTypeEnum callType;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setCallType(CallTypeEnum callType) {
        this.callType = callType;
    }

    public CallTypeEnum getCallType() {
        return callType;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        History history = (History) o;

        return new EqualsBuilder()
                .append(id, history.id)
                .append(currencyFrom, history.currencyFrom)
                .append(currencyTo, history.currencyTo)
                .append(user, history.user)
                .append(amount, history.amount)
                .append(date, history.date)
                .append(rate, history.rate)
                .append(timeStamp, history.timeStamp)
                .append(result, history.result)
                .append(createDate, history.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(currencyFrom)
                .append(currencyTo)
                .append(user)
                .append(amount)
                .append(date)
                .append(rate)
                .append(timeStamp)
                .append(result)
                .append(createDate)
                .toHashCode();
    }
}
