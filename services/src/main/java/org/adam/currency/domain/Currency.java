package org.adam.currency.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCIES")
public class Currency {
    @Id
    @Column(name = "CURRENCY_CODE")
    private String code;

    @Column(name = "CURRENCY_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE")
    private Country country;

    public Currency() {
    }

    public Currency(String code, String name, Country country) {
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Currency currency = (Currency) o;

        return new EqualsBuilder()
                .append(code, currency.code)
                .append(name, currency.name)
                .append(country, currency.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(name)
                .append(country)
                .toHashCode();
    }
}
