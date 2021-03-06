package org.adam.currency.dto;

import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyResponseTest {
    private CurrencyResponse dto1 = new CurrencyResponseBuilder().withQuote(2.0).withResult(3.0).withSuccess(true).withTimestamp(LocalDateTime.of(2017, 4, 30, 11, 35, 0)).build();
    private CurrencyResponse dto2 = new CurrencyResponseBuilder().withQuote(2.0).withResult(3.0).withSuccess(true).withTimestamp(LocalDateTime.of(2017, 4, 30, 11, 35, 0)).build();
    private CurrencyResponse dto3 = new CurrencyResponseBuilder().withQuote(3.0).withResult(4.0).withSuccess(false).withTimestamp(LocalDateTime.of(2017, 4, 30, 11, 35, 0)).build();

    @Test
    void verifyEquals() {
        EqualsTestHelper.verifyEquals(dto1, dto2, dto3);
    }

    @Test
    void verifyHashCode() {
        EqualsTestHelper.verifyHashCode(dto1, dto2, dto3);
    }

    @Test
    void verifyToString() {
        assertEquals("CurrencyResponse{success=true, result=3.0}", dto1.toString());
    }

}