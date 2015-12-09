package org.adam.currency.service;

import org.adam.currency.domain.Country;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.repository.GenericRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericServiceImplTest {

    @InjectMocks
    private GenericServiceImpl service = new GenericServiceImpl();

    @Mock
    private GenericRepository mockGenericRepository;

    @Test
    public void testFindById() throws Exception {
        User expectedUser = new User();
        when(mockGenericRepository.findById(User.class, 1L)).thenReturn(expectedUser);
        User user = service.findById(User.class, 1L);
        verify(mockGenericRepository).findById(User.class, 1L);
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(user, notNullValue());
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    public void testFindByName() throws Exception {
        User expectedUser = new User();
        when(mockGenericRepository.findByName(User.class, "name", "someUser")).thenReturn(expectedUser);
        User user = service.findByName(User.class, "name", "someUser");
        verify(mockGenericRepository).findByName(User.class, "name", "someUser");
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(user, notNullValue());
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Country> expectedCountries = CountryFixture.COUNTRIES;
        when(mockGenericRepository.findAll(Country.class)).thenReturn(expectedCountries);
        List<Country> countries = service.findAll(Country.class);
        verify(mockGenericRepository).findAll(Country.class);
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(countries, sameInstance(expectedCountries));
    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        when(mockGenericRepository.save(user)).thenReturn(1L);
        Long userId = (Long) service.save(user);
        verify(mockGenericRepository).save(user);
        verifyNoMoreInteractions(mockGenericRepository);
        assertThat(userId, equalTo(1L));
    }
}