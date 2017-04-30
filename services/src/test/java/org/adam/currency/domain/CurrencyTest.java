package org.adam.currency.domain;

import org.adam.currency.builder.CurrencyBuilder;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CurrencyTest {
    private Currency c1 = CurrencyFixture.GBP;
    private Currency c2 = new CurrencyBuilder().withCode("GBP").withName("British Pound").withCountry(CountryFixture.UK).build();
    private Currency c3 = CurrencyFixture.EUR;

    @Test
    public void verifyEquals() throws Exception {
        new Currency();
        EqualsTestHelper.verifyEquals(c1, c2, c3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(c1, c2, c3);
    }

    @Test
    public void verifyToString() throws Exception {
        assertThat(c1.toString(), equalTo("Currency{code='GBP', name='British Pound'}"));
    }
}