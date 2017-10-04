package org.adam.currency.builder;

import org.adam.currency.domain.Currency;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CurrencyBuilderTest {
    @Test
    void createCurrency() throws Exception {
        Currency currency = new Currency("GBP", "British Pound", CountryFixture.UK);
        Currency actual = new CurrencyBuilder().withCode(currency.getCode()).withName(currency.getName()).withCountry(currency.getCountry()).build();
        assertEquals(currency, actual);
    }
}