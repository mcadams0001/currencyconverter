package org.adam.currency.builder;

import org.adam.currency.domain.Address;
import org.adam.currency.domain.Country;

/**
 * Address Builder
 */
public class AddressBuilder {
    private Long id;
    private String street;
    private String city;
    private String postCode;
    private Country country;

    public AddressBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public AddressBuilder withStreet(String street){
        this.street = street;
        return this;
    }

    public AddressBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public AddressBuilder withPostCode(String postCode){
        this.postCode = postCode;
        return this;
    }

    public AddressBuilder withCountry(Country country){
        this.country = country;
        return this;
    }

    public Address build() {
        return new Address(id, street, city, postCode, country);
    }
}
