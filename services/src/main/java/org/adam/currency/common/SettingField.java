package org.adam.currency.common;

/**
 * Enumerated settings in use.
 */
public enum SettingField {
    CURRENCY_SERVICE_URL,
    HISTORY_SHOW_LAST("10"),
    CONNECTION_TIMEOUT("30");

    private String defaultValue;

    SettingField() {
        defaultValue = "";
    }

    SettingField(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isDefaultValueEmpty() {
        return "".equals(defaultValue);
    }
}
