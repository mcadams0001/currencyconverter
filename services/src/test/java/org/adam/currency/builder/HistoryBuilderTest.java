package org.adam.currency.builder;

import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.domain.History;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryBuilderTest {

    @Test
    void createHistory() throws Exception {
        History history = new History();
        history.setId(100L);
        history.setAmount(100d);
        history.setCallType(CallTypeEnum.WEB_SERVICE);
        history.setCreateDate(LocalDateTime.of(2017, 4, 28, 21, 35, 0));
        history.setCurrencyFrom(CurrencyFixture.GBP);
        history.setCurrencyTo(CurrencyFixture.EUR);
        history.setDate(LocalDate.of(2017, 4, 28));
        history.setRate(1.12d);
        history.setResult(112d);
        history.setTimeStamp(LocalDateTime.of(2017, 4, 28, 18, 0, 0));
        history.setUser(UserFixture.TEST_USER);

        History actual = new HistoryBuilder()
                .withId(history.getId())
                .withCurrencyFrom(history.getCurrencyFrom())
                .withCurrencyTo(history.getCurrencyTo())
                .withUser(history.getUser())
                .withAmount(history.getAmount())
                .withDate(history.getDate())
                .withRate(history.getRate())
                .withResult(history.getResult())
                .withCreateDate(history.getCreateDate())
                .withTimeStamp(history.getTimeStamp())
                .withCallType(history.getCallType())
                .build();
        assertEquals(history, actual);
    }
}