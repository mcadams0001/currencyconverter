package org.adam.currency.builder;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CountryBuilderTest {
    @Test
    public void createCountry() throws Exception {
        Country ec = CountryFixture.UK;
        Country country = new Country(ec.getCode(), ec.getName(), ec.getPostCodeRegExp());
        Country actualCountry = new CountryBuilder().withCode(ec.getCode()).withName(ec.getName()).withPostCodeRegExp(ec.getPostCodeRegExp()).build();
        assertThat(actualCountry, equalTo(country));


    }
}