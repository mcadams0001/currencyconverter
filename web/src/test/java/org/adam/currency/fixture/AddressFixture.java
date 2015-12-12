package org.adam.currency.fixture;

import org.adam.currency.builder.AddressBuilder;
import org.adam.currency.domain.Address;

public class AddressFixture {
    public static final Address TEST_ADDRESS = new AddressBuilder().withId(1L).withStreet("Canada Square").withCity("London").withPostCode("E14 1LH").withCountry(CountryFixture.UK).build();
}
