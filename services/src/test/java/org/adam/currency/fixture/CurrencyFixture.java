package org.adam.currency.fixture;

import org.adam.currency.builder.CurrencyBuilder;
import org.adam.currency.domain.Currency;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyFixture {
    public static final Currency EUR = new CurrencyBuilder().withCode("EUR").withName("Euro").withCountry(CountryFixture.GERMANY).build();
    public static final Currency USD = new CurrencyBuilder().withCode("USD").withName("US Dollar").withCountry(CountryFixture.US).build();
    public static final Currency GBP = new CurrencyBuilder().withCode("GBP").withName("British Pound").withCountry(CountryFixture.UK).build();

    public static final List<Currency> CURRENCIES = List.of(EUR, USD, GBP);
}
