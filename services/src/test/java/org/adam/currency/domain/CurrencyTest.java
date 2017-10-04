package org.adam.currency.domain;

import org.adam.currency.builder.CurrencyBuilder;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class CurrencyTest {
    private Currency c1 = CurrencyFixture.GBP;
    private Currency c2 = new CurrencyBuilder().withCode("GBP").withName("British Pound").withCountry(CountryFixture.UK).build();
    private Currency c3 = CurrencyFixture.EUR;

    @Test
    void verifyEquals() throws Exception {
        new Currency();
        EqualsTestHelper.verifyEquals(c1, c2, c3);
    }

    @Test
    void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(c1, c2, c3);
    }

    @Test
    void verifyToString() throws Exception {
        assertEquals("Currency{code='GBP', name='British Pound'}", c1.toString());
    }
}