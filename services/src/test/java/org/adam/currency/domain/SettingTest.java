package org.adam.currency.domain;

import org.adam.currency.common.SettingField;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SettingTest {
    @Test
    void createSetting() {
        new Setting();
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertEquals(SettingField.CURRENCY_SERVICE_URL, setting.getName());
        assertEquals("http://localhost", setting.getValue());
    }

    @Test
    void verifyEqual() {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyEquals(setting1, setting2, setting3);
    }

    @Test
    void verifyHashCode() {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyHashCode(setting1, setting2, setting3);
    }

    @Test
    void verifyToString() {
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertEquals("Setting{CURRENCY_SERVICE_URL=http://localhost}", setting.toString());

    }
}