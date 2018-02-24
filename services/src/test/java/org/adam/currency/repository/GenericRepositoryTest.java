package org.adam.currency.repository;

import org.adam.currency.builder.CountryBuilder;
import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenericRepositoryTest extends BaseRepositoryTests {

    @Autowired
    private GenericRepository genericRepository;

    @BeforeEach
    void setUp() {
        initialSetup();
    }


    @Test
    void testFindById() {
        Country country = genericRepository.findById(Country.class, "GBR");
        assertNotNull(country);
        assertEquals("GBR", country.getCode());
    }

    @Test
    void shouldFindByName() {
        Country uk = CountryFixture.UK;
        Country country = genericRepository.findByName(Country.class, "name", uk.getName());
        assertNotNull(country);
        assertEquals("GBR", country.getCode());
    }


    @Test
    void testFindAll() {
        List<Country> countries = genericRepository.findAll(Country.class, "name");
        assertNotNull(countries);
        assertEquals(CountryFixture.COUNTRIES, countries);
    }

    @Test
    void testFindAllWithNullOrder() {
        List<Country> countries = genericRepository.findAll(Country.class);
        assertNotNull(countries);
        assertEquals(CountryFixture.COUNTRIES, countries);
    }

    @Test
    void testSave() {
        Country country = new CountryBuilder().withCode("TST").withName("Test").build();
        genericRepository.save(country);
        Country actualCountry = genericRepository.findById(Country.class, "TST");
        assertEquals(country, actualCountry);
    }

    @Test
    void shouldReturnNullIfSingleElementCannotBeFound() {
        Country actualCountry = genericRepository.findById(Country.class, "XYZ");
        assertNull(actualCountry);
    }
}