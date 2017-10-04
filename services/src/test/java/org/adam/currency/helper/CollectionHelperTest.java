package org.adam.currency.helper;

import org.adam.currency.domain.Country;
import org.adam.currency.fixture.CountryFixture;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.*;

class CollectionHelperTest {

    @Test
    void shouldCreateADefensiveCopy() throws Exception {
        new CollectionHelper();
        List<Country> sourceList = CountryFixture.COUNTRIES;
        List<Country> countries = CollectionHelper.defensiveCopy(sourceList);
        assertEquals(sourceList, countries);
        assertNotSame(sourceList, countries);
    }

    @Test
    void shouldCreateNewInstanceOnEmptyList() throws Exception {
        List<Country> emptyList = new ArrayList<>();
        List<Country> countries = CollectionHelper.defensiveCopy(emptyList);
        assertNotNull(countries);
        assertEquals(0, countries.size());
        assertNotSame(emptyList, countries);
    }

    @Test
    void shouldCreateNewInstanceOnNullList() throws Exception {
        List<Country> countries = CollectionHelper.defensiveCopy(null);
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }
}