package org.adam.currency.builder;

import org.adam.currency.domain.Address;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * User builder.
 */
public class UserBuilder {
    private Long id;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<Role> roles = new ArrayList<>();
    private Address address;

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String userName) {
        this.name = userName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public UserBuilder withRoles(List<Role> roles) {
        if(roles != null && roles.size() > 0) {
            this.roles.addAll(roles);
        }
        return this;
    }

    public User build() {
        return new User(id, name, password, firstName, lastName, emailAddress, roles, address);
    }

}
