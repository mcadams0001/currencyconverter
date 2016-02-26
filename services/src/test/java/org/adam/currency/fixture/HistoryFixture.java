package org.adam.currency.fixture;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.domain.History;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoryFixture {
    public static final History GBP_EUR_2016_1_30 = new HistoryBuilder().withAmount(200.0d).withCallType(CallTypeEnum.WEB_SERVICE).withCreateDate(LocalDateTime.of(2016,1,30,18,30,30))
            .withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016,1,30)).withRate(0.658443).withResult(300.0d).withTimeStamp(LocalDateTime.of(2016, 1, 30, 18, 54, 30))
            .withUser(UserFixture.TEST_USER).build();
}
