package org.adam.currency.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
    @Id
    @SequenceGenerator(name = "ADDRESS_ID_GEN", sequenceName = "ADDRESS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "ADDRESS_ID_GEN")
    private Long id;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "POST_CODE")
    private String postCode;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE")
    private Country country;

    /**
     * Default constructor used by Hibernate.
     */
    public Address() {
        //Does nothing.
    }

    public Address(Long id, String street, String city, String postCode, Country country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return new EqualsBuilder()
                .append(id, address.id)
                .append(street, address.street)
                .append(city, address.city)
                .append(postCode, address.postCode)
                .append(country, address.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(street)
                .append(city)
                .append(postCode)
                .append(country)
                .toHashCode();
    }
}
