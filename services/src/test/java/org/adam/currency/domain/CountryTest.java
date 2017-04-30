package org.adam.currency.domain;

import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CountryTest {

    private Country uk = CountryFixture.UK;
    private Country uk2 = new Country(uk.getCode(), uk.getName(), uk.getPostCodeRegExp());
    private Country de = CountryFixture.GERMANY;

    @Test
    public void countryEquals() throws Exception {
        new Country();
        EqualsTestHelper.verifyEquals(uk, uk2, de);
    }

    @Test
    public void countryHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(uk, uk2, de);
    }

    @Test
    public void countryToString() throws Exception {
        assertThat(uk.toString(), equalTo("Country{code='GBR', name='United Kingdom'}"));
    }
}