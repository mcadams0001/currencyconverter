package org.adam.currency.service;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl service = new CountryServiceImpl();

    @Mock
    private GenericService mockGenericService;

    @Test
    public void findAll() throws Exception {
        List<Country> countries = CountryFixture.COUNTRIES;
        when(mockGenericService.findAll(Country.class, "name")).thenReturn(countries);
        List<Country> actualCountries = service.findAll();
        verify(mockGenericService).findAll(Country.class, "name");
        assertThat(actualCountries, equalTo(countries));
    }

    @Test
    public void findByCode() throws Exception {
        Country country = CountryFixture.UK;
        when(mockGenericService.findById(Country.class, "UK")).thenReturn(country);
        Country actualCountry = service.findByCode("UK");
        verify(mockGenericService).findById(Country.class, "UK");
        assertThat(actualCountry, equalTo(country));
    }

}