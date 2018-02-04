package org.adam.currency.domain;

import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {

    private Country uk = CountryFixture.UK;
    private Country uk2 = new Country(uk.getCode(), uk.getName(), uk.getPostCodeRegExp());
    private Country de = CountryFixture.GERMANY;

    @Test
    void countryEquals() {
        new Country();
        EqualsTestHelper.verifyEquals(uk, uk2, de);
    }

    @Test
    void countryHashCode() {
        EqualsTestHelper.verifyHashCode(uk, uk2, de);
    }

    @Test
    void countryToString() {
        assertEquals("Country{code='GBR', name='United Kingdom'}", uk.toString());
    }
}