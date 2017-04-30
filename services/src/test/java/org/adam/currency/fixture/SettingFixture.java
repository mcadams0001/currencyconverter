package org.adam.currency.fixture;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SettingFixture {

    public static final Setting CURRENCY_SERVICE_URL = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://apilayer.net/api/live?access_key=%s&format=1&source=USD&currencies=%s");
    public static final Setting HISTORY_SHOW_LAST = new Setting(SettingField.HISTORY_SHOW_LAST, "10");
    public static final Setting CONNECTION_TIMEOUT = new Setting(SettingField.CONNECTION_TIMEOUT, "30");
    public static final List<Setting> SETTINGS = Collections.unmodifiableList(Arrays.asList(CURRENCY_SERVICE_URL, HISTORY_SHOW_LAST));
}
