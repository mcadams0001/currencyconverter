package org.adam.currency.domain;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import java.time.LocalDateTime;

public class HistoryTest {
    private History h1 = new HistoryBuilder().withAmount(100.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(LocalDateTime.of(2017, 4, 30, 12, 0, 0)).withResult(118.0d).withUser(UserFixture.TEST_USER).build();
    private History h2 = new HistoryBuilder().withAmount(100.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(LocalDateTime.of(2017, 4, 30, 12, 0, 0)).withResult(118.0d).withUser(UserFixture.TEST_USER).build();
    private History h3 = new HistoryBuilder().withAmount(200.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(LocalDateTime.of(2017, 4, 30, 12, 0, 0)).withResult(118.0d).withUser(UserFixture.TEST_USER).build();

    @Test
    public void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(h1, h2, h3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(h1, h2, h3);
    }
}