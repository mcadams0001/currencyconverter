package org.adam.currency.repository;

import org.adam.currency.builder.CountryBuilder;
import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GenericRepositoryTest extends BaseRepositoryTests {

    @Autowired
    private GenericRepository genericRepository;

    @Before
    public void setUp() throws Exception {
        initialSetup();
    }


    @Test
    public void testFindById() throws Exception {
        Country country = genericRepository.findById(Country.class, "GBR");
        assertThat(country, notNullValue());
        assertThat(country.getCode(), equalTo("GBR"));
    }

    @Test
    public void shouldFindByName() throws Exception {
        Country country = genericRepository.findByName(Country.class, "name", "United Kingdom");
        assertThat(country, notNullValue());
        assertThat(country.getCode(), equalTo("GBR"));
    }


    @Test
    public void testFindAll() throws Exception {
        List<Country> countries = genericRepository.findAll(Country.class, "name");
        assertThat(countries, notNullValue());
        assertThat(countries, equalTo(CountryFixture.COUNTRIES));
    }

    @Test
    public void testFindAllWithNullOrder() throws Exception {
        List<Country> countries = genericRepository.findAll(Country.class);
        assertThat(countries, notNullValue());
        assertThat(countries, equalTo(CountryFixture.COUNTRIES));
    }

    @Test
    public void testSave() throws Exception {
        Country country = new CountryBuilder().withCode("TST").withName("Test").build();
        genericRepository.save(country);
        Country actualCountry = genericRepository.findById(Country.class, "TST");
        assertThat(actualCountry, equalTo(country));
    }
}