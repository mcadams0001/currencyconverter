package org.adam.currency.helper;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CollectionHelperTest {
    @Test
    public void shouldCreateADefensiveCopy() throws Exception {
        List<Country> sourceList = CountryFixture.COUNTRIES;
        List<Country> countries = CollectionHelper.defensiveCopy(sourceList);
        assertThat(countries, equalTo(sourceList));
        assertThat(countries, not(sameInstance(sourceList)));
    }

    @Test
    public void shouldCreateNewInstanceOnEmptyList() throws Exception {
        List<Country> emptyList = new ArrayList<>();
        List<Country> countries = CollectionHelper.defensiveCopy(emptyList);
        assertThat(countries, notNullValue());
        assertThat(countries.size(), equalTo(0));
        assertThat(countries, not(sameInstance(emptyList)));
    }

    @Test
    public void shouldCreateNewInstanceOnNullList() throws Exception {
        List<Country> countries = CollectionHelper.defensiveCopy(null);
        assertThat(countries, notNullValue());
        assertThat(countries.size(), equalTo(0));
    }
}