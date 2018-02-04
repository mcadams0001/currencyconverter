package org.adam.currency.builder;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryBuilderTest {
    @Test
    void createCountry() {
        Country ec = CountryFixture.UK;
        Country country = new Country(ec.getCode(), ec.getName(), ec.getPostCodeRegExp());
        Country actualCountry = new CountryBuilder().withCode(ec.getCode()).withName(ec.getName()).withPostCodeRegExp(ec.getPostCodeRegExp()).build();
        assertEquals(country, actualCountry);


    }
}