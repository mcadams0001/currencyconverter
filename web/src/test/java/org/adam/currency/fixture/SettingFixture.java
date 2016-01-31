package org.adam.currency.fixture;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;

import java.util.Collections;
import java.util.List;

public class SettingFixture {

    public static final Setting CURRENCY_SERVICE_URL = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://apilayer.net/api/live?access_key=%s&format=1&source=USD&currencies=%s");
    public static final Setting ACCESS_KEY = new Setting(SettingField.ACCESS_KEY, "abcd");
    public static final List<Setting> SETTINGS = Collections.unmodifiableList(Collections.singletonList(CURRENCY_SERVICE_URL));
}
