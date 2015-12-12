package org.adam.currency.service;

import org.adam.currency.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("countryService")
public class CountryServiceImpl implements CountryService {
    @Autowired
    private GenericService genericService;

    @Override
    public List<Country> findAll() {
        return genericService.findAll(Country.class, "name");
    }

    @Override
    public Country findByCode(String countryCode) {
        return genericService.findById(Country.class, countryCode);
    }
}
