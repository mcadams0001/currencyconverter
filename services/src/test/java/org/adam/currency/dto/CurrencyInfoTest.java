package org.adam.currency.dto;

import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CurrencyInfoTest {
    private CurrencyInfo info1 = new CurrencyInfo(2.0, LocalDateTime.of(2017, 4, 30, 11, 33, 0));
    private CurrencyInfo info2 = new CurrencyInfo(2.0, LocalDateTime.of(2017, 4, 30, 11, 33, 0));
    private CurrencyInfo info3 = new CurrencyInfo(3.0, LocalDateTime.of(2017, 4, 30, 12, 33, 0));

    @Test
    void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(info1, info2, info3);
    }

    @Test
    void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(info1, info2, info3);
    }
}