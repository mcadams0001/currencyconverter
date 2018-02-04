package org.adam.currency.service;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;
import org.adam.currency.fixture.SettingFixture;
import org.adam.currency.repository.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SettingServiceImplTest {

    @InjectMocks
    private SettingService service = new SettingServiceImpl();
    @Mock
    private GenericRepository mockGenericRepository;

    private static final String SERVICE_ADDRESS = "http://localhost:7001/api";

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testShouldGetTheConfiguredSettingValueForTheSettingTypePassedIn() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL)).thenReturn(new Setting(SettingField.CURRENCY_SERVICE_URL, SERVICE_ADDRESS));
        String batchSize = service.getSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL);
        assertEquals(SERVICE_ADDRESS, batchSize);
    }

    @Test
    void shouldGetNullIfDefaultValueIsNullForInteger() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL)).thenReturn(null);
        int actual = service.getIntSetting(SettingField.CURRENCY_SERVICE_URL);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CURRENCY_SERVICE_URL);
        assertEquals(0, actual);
    }

    @Test
    void shouldGetIntSettingWithDefaultValue() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST)).thenReturn(null);
        int actual = service.getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST);
        assertEquals(10, actual);
    }

    @Test
    void shouldGetIntSetting() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST)).thenReturn(SettingFixture.HISTORY_SHOW_LAST);
        int actual = service.getIntSetting(SettingField.HISTORY_SHOW_LAST);
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.HISTORY_SHOW_LAST);
        assertEquals(10, actual);
    }

    @Test
    void shouldGetConnectionTimeOut() {
        when(mockGenericRepository.findByName(Setting.class, "name", SettingField.CONNECTION_TIMEOUT)).thenReturn(SettingFixture.CONNECTION_TIMEOUT);
        int connectionTimeout = service.getConnectionTimeout();
        verify(mockGenericRepository).findByName(Setting.class, "name", SettingField.CONNECTION_TIMEOUT);
        assertEquals(30000, connectionTimeout);
    }
}

