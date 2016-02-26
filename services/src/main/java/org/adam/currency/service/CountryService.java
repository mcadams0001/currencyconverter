package org.adam.currency.service;

import org.adam.currency.domain.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country findByCode(String countryCode);
}
