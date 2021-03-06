package org.adam.currency.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "COUNTRIES")
public class Country implements Serializable {

    @Id
    @Column(name = "COUNTRY_CODE")
    private String code;

    @Column(name = "COUNTRY_NAME")
    private String name;

    @Column(name = "POST_CODE_REGEXP")
    private String postCodeRegExp;

    public Country() {
    }

    public Country(String code, String name, String postCodeRegExp) {
        this.code = code;
        this.name = name;
        this.postCodeRegExp = postCodeRegExp;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPostCodeRegExp() {
        return postCodeRegExp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        return new EqualsBuilder()
                .append(code, country.code)
                .append(name, country.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
