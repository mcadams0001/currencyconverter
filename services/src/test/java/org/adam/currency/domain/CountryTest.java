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
    void countryEquals() throws Exception {
        new Country();
        EqualsTestHelper.verifyEquals(uk, uk2, de);
    }

    @Test
    void countryHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(uk, uk2, de);
    }

    @Test
    void countryToString() throws Exception {
        assertEquals("Country{code='GBR', name='United Kingdom'}", uk.toString());
    }
}