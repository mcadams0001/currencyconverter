package org.adam.currency.service;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl service = new CountryServiceImpl();

    @Mock
    private GenericService mockGenericService;

    @Test
    void findAll() {
        List<Country> countries = CountryFixture.COUNTRIES;
        when(mockGenericService.findAll(Country.class, "name")).thenReturn(countries);
        List<Country> actualCountries = service.findAll();
        verify(mockGenericService).findAll(Country.class, "name");
        assertEquals(countries, actualCountries);
    }

    @Test
    void findByCode() {
        Country country = CountryFixture.UK;
        when(mockGenericService.findById(Country.class, "UK")).thenReturn(country);
        Country actualCountry = service.findByCode("UK");
        verify(mockGenericService).findById(Country.class, "UK");
        assertEquals(country, actualCountry);
    }

}