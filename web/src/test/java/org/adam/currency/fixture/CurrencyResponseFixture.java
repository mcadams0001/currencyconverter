package org.adam.currency.fixture;

import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.dto.CurrencyResponse;

import java.time.LocalDateTime;

public class CurrencyResponseFixture {
    public static final CurrencyResponse SUCCESS_RESPONSE = new CurrencyResponseBuilder().withSuccess(true).withQuote(0.6).withResult(200.0).withTimestamp(LocalDateTime.of(2016, 1, 30, 12, 10, 30)).build();
    public static final CurrencyResponse FAILURE_RESPONSE = new CurrencyResponseBuilder().withSuccess(false).withErrorMessage("Failed").build();
}
