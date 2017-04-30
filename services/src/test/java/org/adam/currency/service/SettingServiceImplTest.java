package org.adam.currency.service;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;
import org.adam.currency.fixture.SettingFixture;
import org.adam.currency.repository.GenericRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingServiceImplTest {

    @InjectMocks
    private SettingService service = new SettingServiceImpl();
    @Mock
    private GenericRepository mockGenericRepository;

    private static final String SERVICE_ADDRESS = "http://localhost:7001/api";

    @Test
    public void testShouldGetTheConfiguredSettingValueForTheSettingTypePassedIn() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL)).thenReturn(new Setting(SettingField.CURRENCY_SERVICE_URL, SERVICE_ADDRESS));
        String batchSize = service.getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL);
        assertThat(batchSize, equalTo(SERVICE_ADDRESS));
    }

    @Test
    public void shouldGetNullIfDefaultValueIsNullForInteger() throws Exception {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL)).thenReturn(null);
        int actual = service.getIntSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL);
        assertThat(actual, equalTo(0));
    }

    @Test
    public void shouldGetIntSettingWithDefaultValue() throws Exception {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST)).thenReturn(null);
        int actual = service.getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST);
        assertThat(actual, equalTo(10));
    }

    @Test
    public void shouldGetIntSetting() throws Exception {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST)).thenReturn(SettingFixture.HISTORY_SHOW_LAST);
        int actual = service.getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST);
        assertThat(actual, equalTo(10));
    }

    @Test
    public void shouldGetConnectionTimeOut() throws Exception {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CONNECTION_TIMEOUT)).thenReturn(SettingFixture.CONNECTION_TIMEOUT);
        int connectionTimeout = service.getConnectionTimeout();
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CONNECTION_TIMEOUT);
        assertThat(connectionTimeout, equalTo(30000));
    }
}

