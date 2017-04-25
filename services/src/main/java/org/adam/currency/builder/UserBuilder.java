package org.adam.currency.builder;

import org.adam.currency.domain.Address;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate birthDate;
    private List<Role> roles = new ArrayList<>();
    private Address address;
    private LocalDateTime createDate = LocalDateTime.now();

    public UserBuilder withCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
        return this;
    }


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

    public UserBuilder withBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
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
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);
        user.setBirthDate(birthDate);
        user.setRoles(roles);
        user.setAddress(address);
        user.setCreateDate(createDate);
        return user;
    }

}
