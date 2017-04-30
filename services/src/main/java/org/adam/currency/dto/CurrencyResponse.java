package org.adam.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.adam.currency.helper.BooleanSerializer;
import org.adam.currency.helper.DoubleSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonIgnoreProperties({"terms", "privacy", "source"})
public class CurrencyResponse {
    @JsonSerialize(using = BooleanSerializer.class)
    private Boolean success;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double result;
    @JsonProperty("info")
    private CurrencyInfo info;
    @JsonProperty("error")
    private CurrencyError error;

    public CurrencyResponse(Double quote, Double result, LocalDateTime timestamp) {
        this();
        this.info.setQuote(quote);
        this.info.setTimestamp(timestamp);
        this.result = result;
    }

    public CurrencyResponse(String errorMessage) {
        this();
        this.error.setInfo(errorMessage);
    }

    public CurrencyResponse() {
        this.info = new CurrencyInfo();
        this.error = new CurrencyError();
    }

    public static CurrencyResponse createError(String errorMessage) {
        return new CurrencyResponse(errorMessage);
    }

    public Boolean getSuccess() {
        return Optional.ofNullable(success).orElse(Boolean.FALSE);
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public CurrencyInfo getInfo() {
        return info;
    }

    public void setInfo(CurrencyInfo info) {
        this.info = info;
    }

    public CurrencyError getError() {
        return error;
    }

    public void setError(CurrencyError error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyResponse that = (CurrencyResponse) o;

        return new EqualsBuilder()
                .append(success, that.success)
                .append(result, that.result)
                .append(info, that.info)
                .append(error, that.error)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(success)
                .append(result)
                .append(info)
                .append(error)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "success=" + success +
                ", result=" + result +
                '}';
    }
}
