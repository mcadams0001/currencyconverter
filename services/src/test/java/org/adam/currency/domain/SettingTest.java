package org.adam.currency.domain;

import org.adam.currency.common.SettingField;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class SettingTest {
    @Test
    void createSetting() throws Exception {
        new Setting();
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertEquals(SettingField.CURRENCY_SERVICE_URL, setting.getName());
        assertEquals("http://localhost", setting.getValue());
    }

    @Test
    void verifyEqual() throws Exception {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyEquals(setting1, setting2, setting3);
    }

    @Test
    void verifyHashCode() throws Exception {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyHashCode(setting1, setting2, setting3);
    }

    @Test
    void verifyToString() throws Exception {
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertEquals("Setting{CURRENCY_SERVICE_URL=http://localhost}", setting.toString());

    }
}