package org.adam.currency.service;

import org.adam.currency.domain.Country;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.repository.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GenericServiceImplTest {

    @InjectMocks
    private GenericServiceImpl service = new GenericServiceImpl();

    @Mock
    private GenericRepository mockGenericRepository;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        User expectedUser = new User();
        when(mockGenericRepository.findById(User.class, 1L)).thenReturn(expectedUser);
        User user = service.findById(User.class, 1L);
        verify(mockGenericRepository).findById(User.class, 1L);
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(user, notNullValue());
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    void testFindByName() throws Exception {
        User expectedUser = new User();
        when(mockGenericRepository.findByName(User.class, "name", "someUser")).thenReturn(expectedUser);
        User user = service.findByName(User.class, "name", "someUser");
        verify(mockGenericRepository).findByName(User.class, "name", "someUser");
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(user, notNullValue());
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    void testFindAll() throws Exception {
        List<Country> expectedCountries = CountryFixture.COUNTRIES;
        when(mockGenericRepository.findAll(Country.class)).thenReturn(expectedCountries);
        List<Country> countries = service.findAll(Country.class);
        verify(mockGenericRepository).findAll(Country.class);
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(countries, sameInstance(expectedCountries));
    }

    @Test
    void testSave() throws Exception {
        User user = new User();
        when(mockGenericRepository.save(user)).thenReturn(1L);
        Long userId = (Long) service.save(user);
        verify(mockGenericRepository).save(user);
        verifyNoMoreInteractions(mockGenericRepository);
        assertEquals(1L, userId.longValue());
    }
}