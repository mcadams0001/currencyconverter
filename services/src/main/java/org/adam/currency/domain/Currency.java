package org.adam.currency.domain;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCIES")
public class Currency {
    @Id
    @Column(name = "CURRENCY_CODE")
    private String code;

    @Column(name = "CURRENCY_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE")
    private Country country;

    public Currency() {
    }

    public Currency(String code, String name, Country country) {
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
