package org.adam.currency.service;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;
import org.adam.currency.repository.GenericRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Settings service.
 */
@Transactional
public class SettingServiceImpl implements SettingService {
    public static final Logger LOGGER = Logger.getLogger(SettingServiceImpl.class);

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
            LOGGER.warn("Failed to retrieve setting " + field.name());
            if (field.isDefaultValueEmpty()) {
                LOGGER.warn("Failed to retrieve setting " + field.name() + ". Using default value: 0");
                return 0;
            }
            LOGGER.warn("Failed to retrieve setting " + field.name() + ". Using default value: " + field.getDefaultValue());
            return Integer.parseInt(field.getDefaultValue());
        }
    }

    @Override
    public int getConnectionTimeout() {
        return getIntSetting(SettingField.CONNECTION_TIMEOUT);
    }
}
