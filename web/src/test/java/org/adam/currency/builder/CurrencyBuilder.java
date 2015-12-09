package org.adam.currency.builder;

import org.adam.currency.domain.Country;
import org.adam.currency.domain.Currency;

public class CurrencyBuilder {
    private String code;
    private String name;
    private Country country;

    public CurrencyBuilder withCode(String code){
        this.code = code;
        return this;
    }

    public CurrencyBuilder withName(String name){
        this.name = name;
        return this;
    }

    public CurrencyBuilder withCountry(Country country){
        this.country = country;
        return this;
    }

    public Currency build() {
        return new Currency(code, name, country);
    }
}
