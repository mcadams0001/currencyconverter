package org.adam.currency.domain;

import org.adam.currency.builder.CurrencyBuilder;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CurrencyTest {
    private Currency c1 = CurrencyFixture.GBP;
    private Currency c2 = new CurrencyBuilder().withCode("GBP").withName("British Pound").withCountry(CountryFixture.UK).build();
    private Currency c3 = CurrencyFixture.EUR;

    @Test
    void verifyEquals() {
        new Currency();
        EqualsTestHelper.verifyEquals(c1, c2, c3);
    }

    @Test
    void verifyHashCode() {
        EqualsTestHelper.verifyHashCode(c1, c2, c3);
    }

    @Test
    void verifyToString() {
        assertEquals("Currency{code='GBP', name='British Pound'}", c1.toString());
    }
}