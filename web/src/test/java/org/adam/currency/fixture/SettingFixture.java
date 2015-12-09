package org.adam.currency.fixture;

import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Setting;

import java.util.Collections;
import java.util.List;

public class SettingFixture {

    public static final Setting CURRENCY_SERVICE_URL = new Setting(SettingField.CURRENCY_SERVICE_URL, "http://localhost:7001");
    public static final List<Setting> SETTINGS = Collections.unmodifiableList(Collections.singletonList(CURRENCY_SERVICE_URL));
}
