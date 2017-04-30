package org.adam.currency.domain;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoryTest {
    private History h1;
    private History h2;
    private History h3;

    @Before
    public void setUp() throws Exception {
        LocalDateTime timestamp = LocalDateTime.of(2017, 4, 30, 12, 0, 0);
        LocalDateTime createDate = LocalDateTime.of(2017,4,30,10,0,0);
        User user = UserFixture.TEST_USER;
        h1 = new HistoryBuilder().withCreateDate(createDate).withAmount(100.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(timestamp).withResult(118.0d).withUser(user).build();
        h2 = new HistoryBuilder().withCreateDate(createDate).withAmount(100.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(timestamp).withResult(118.0d).withUser(user).build();
        h3 = new HistoryBuilder().withCreateDate(createDate).withAmount(200.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withRate(1.18).withTimeStamp(timestamp).withResult(118.0d).withUser(user).build();
    }

    @Test
    public void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(h1, h2, h3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(h1, h2, h3);
    }
}