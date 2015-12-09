package org.adam.currency.builder;

import org.adam.currency.domain.Country;

public class CountryBuilder {
    private String code;
    private String name;

    public CountryBuilder withCode(String code){
        this.code = code;
        return this;
    }

    public CountryBuilder withName(String name){
        this.name = name;
        return this;
    }

    public Country build() {
        return new Country(code, name);
    }
}
