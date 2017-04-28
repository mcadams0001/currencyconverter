package org.adam.currency.domain;

import org.adam.currency.common.SettingField;
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
        assertThat(setting1, equalTo(setting2));
        assertThat(setting1.hashCode(), equalTo(setting2.hashCode()));
        assertThat(setting2, not(equalTo(setting3)));
        assertThat(setting2.hashCode(), not(equalTo(setting3.hashCode())));
        assertThat(setting1.equals(null), equalTo(false));
        assertThat(setting1.equals(setting1), equalTo(true));
    }

    @Test
    public void verifyToString() throws Exception {
        Setting setting = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost");
        assertThat(setting.toString(), equalTo("Setting{CURRENCY_SERVICE_URL=http://localhost}"));

    }
}