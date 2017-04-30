package org.adam.currency.domain;

import org.adam.currency.common.SettingField;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class SettingTest {
    @Test
    public void createSetting() throws Exception {
        new Setting();
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertThat(setting.getName(), equalTo(SettingField.CURRENCY_SERVICE_URL));
        assertThat(setting.getValue(), equalTo("http://localhost"));
    }

    @Test
    public void verifyEqual() throws Exception {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyEquals(setting1, setting2, setting3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        Setting setting1 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting2 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        Setting setting3 = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost2");
        EqualsTestHelper.verifyHashCode(setting1, setting2, setting3);
    }

    @Test
    public void verifyToString() throws Exception {
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertThat(setting.toString(), equalTo("Setting{CURRENCY_SERVICE_URL=http://localhost}"));

    }
}