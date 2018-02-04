package org.adam.currency.builder;

import org.adam.currency.domain.Address;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressBuilderTest {
    @Test
    void createAddress() {
        Address address = new Address(200L, "5 The SouthSea", "London", "W3 4TW", CountryFixture.UK, LocalDateTime.of(2017, 4, 28, 21, 8, 30));
        Address actualAddress = new AddressBuilder().withId(address.getId()).withStreet(address.getStreet())
                .withCity(address.getCity()).withPostCode(address.getPostCode())
                .withCountry(address.getCountry()).withCreateDate(address.getCreateDate())
                .build();
        assertEquals(address, actualAddress);
    }
}