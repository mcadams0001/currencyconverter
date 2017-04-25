package org.adam.currency.domain;

import org.adam.currency.helper.CollectionHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents User
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "USER_ID_GEN", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Role.class)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * Default constructor used by Hibernate.
     */
    public User() {
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Role> getRoles() {
        return CollectionHelper.defensiveCopy(roles);
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(name, user.name)
                .append(password, user.password)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(emailAddress, user.emailAddress)
                .append(birthDate, user.birthDate)
                .append(address, user.address)
                .append(createDate, user.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(password)
                .append(firstName)
                .append(lastName)
                .append(emailAddress)
                .append(birthDate)
                .append(address)
                .append(createDate)
                .toHashCode();
    }


}
