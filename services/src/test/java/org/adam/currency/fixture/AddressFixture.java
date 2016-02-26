package org.adam.currency.fixture;

import org.adam.currency.builder.AddressBuilder;
import org.adam.currency.domain.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddressFixture {
    public static final Address TEST_ADDRESS = new AddressBuilder().withStreet("Canada Square").withCity("London").withPostCode("E14 1LH").withCountry(CountryFixture.UK).build();
    public static final Address TEST_ADDRESS2 = new AddressBuilder().withStreet("Pembroke Road").withCity("London").withPostCode("W1 2TD").withCountry(CountryFixture.UK).build();

    public static final List<Address> ADDRESSES = new ArrayList<>(Arrays.asList(TEST_ADDRESS, TEST_ADDRESS2));
}
