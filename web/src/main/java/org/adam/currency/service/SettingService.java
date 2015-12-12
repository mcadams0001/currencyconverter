package org.adam.currency.service;

import org.adam.currency.common.SettingField;

/**
 * Setting Service.
 */
public interface SettingService {

    /**
     * Gets setting by provided type represented as string.
     * @param settingField the setting field type.
     * @return the setting value represented as string.
     */
    String getSetting(SettingField settingField);

    /**
     * Gets setting by provided type represented as integer.
     * @param settingField the setting field type.
     * @return the setting value represented as integer. If value is not present the default one is used or otherwise zero.
     */
    int getIntSetting(SettingField settingField);

    /**
     * Gets connection timeout in seconds.
     * @return connection timeout in seconds.
     */
    int getConnectionTimeout();
}
