package org.adam.currency.command;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserCommand {
    private String name;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private String street;
    private String city;
    private String postCode;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserCommand command = (UserCommand) o;

        return new EqualsBuilder()
                .append(name, command.name)
                .append(password, command.password)
                .append(repeatPassword, command.repeatPassword)
                .append(firstName, command.firstName)
                .append(lastName, command.lastName)
                .append(email, command.email)
                .append(birthDate, command.birthDate)
                .append(street, command.street)
                .append(city, command.city)
                .append(postCode, command.postCode)
                .append(country, command.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(password)
                .append(repeatPassword)
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(birthDate)
                .append(street)
                .append(city)
                .append(postCode)
                .append(country)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "UserCommand{" +
                "name='" + name + '\'' +
                '}';
    }
}
