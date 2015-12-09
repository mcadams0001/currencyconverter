package org.adam.currency.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Represents User
 */
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "USER_ID_GEN", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Role.class)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    /**
     * Default constructor used by Hibernate.
     */
    public User() {
        //Does nothing.
    }

    public User(Long id, String name, String firstName, String lastName, String emailAddress, List<Role> roles, Address address) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.roles = roles;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(name, user.name)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(emailAddress, user.emailAddress)
                .append(address, user.address)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(firstName)
                .append(lastName)
                .append(emailAddress)
                .append(address)
                .toHashCode();
    }
}
