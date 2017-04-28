package org.adam.currency.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CurrencyError {
    private String code;
    private String info;

    public CurrencyError() {
    }

    public CurrencyError(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyError that = (CurrencyError) o;

        return new EqualsBuilder()
                .append(code, that.code)
                .append(info, that.info)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(info)
                .toHashCode();
    }
}
