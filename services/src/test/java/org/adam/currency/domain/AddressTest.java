package org.adam.currency.domain;

import org.adam.currency.builder.AddressBuilder;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

class AddressTest {
    private Address address1 = new AddressBuilder().withId(1L).withStreet("Street1").withCity("City1").withPostCode("W7 3TD").withCountry(CountryFixture.UK).withCreateDate(LocalDateTime.of(2017,4,30,11,28,0)).build();
    private Address address2 = new AddressBuilder().withId(1L).withStreet("Street1").withCity("City1").withPostCode("W7 3TD").withCountry(CountryFixture.UK).withCreateDate(LocalDateTime.of(2017,4,30,11,28,0)).build();
    private Address address3 = new AddressBuilder().withId(2L).withStreet("Street2").withCity("City1").withPostCode("W7 3TD").withCountry(CountryFixture.UK).withCreateDate(LocalDateTime.of(2017,4,30,11,28,0)).build();

    @Test
    void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(address1, address2, address3);
    }

    @Test
    void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(address1, address2, address3);
    }

    @Test
    void verifyToString() throws Exception {
        assertEquals("Address{street='Street1', city='City1', postCode='W7 3TD', country=Country{code='GBR', name='United Kingdom'}}", address1.toString());
    }
}