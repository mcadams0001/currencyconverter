package org.adam.currency.service;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;
import org.adam.currency.repository.GenericRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Settings service.
 */
@Transactional
public class SettingServiceImpl implements SettingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingServiceImpl.class);
    private static final String FAILED_TO_RETRIEVE_SETTING = "Failed to retrieve setting ";

    @Autowired
    private GenericRepository genericRepository;

    @Transactional(readOnly = true)
    @Override
    public String getSetting(SettingField settingField) {
        Setting setting = genericRepository.findByName(Setting.class, "name", settingField);
        return setting != null ? setting.getValue() : null;
    }

    @Transactional(readOnly = true)
    @Override
    public int getIntSetting(SettingField field) {
        String setting = getSetting(field);
        try {
            return Integer.parseInt(setting);
        } catch (NumberFormatException e) {
            String message = FAILED_TO_RETRIEVE_SETTING + field.name();
            LOGGER.warn(message);
            if (field.isDefaultValueEmpty()) {
                LOGGER.warn("{}. Using default value: 0", message);
                return 0;
            }
            LOGGER.warn("{}. Using default value: {}", message, field.getDefaultValue());
            return Integer.parseInt(field.getDefaultValue());
        }
    }

    @Override
    public int getConnectionTimeout() {
        return getIntSetting(SettingField.CONNECTION_TIMEOUT) * 1000;
    }
}
