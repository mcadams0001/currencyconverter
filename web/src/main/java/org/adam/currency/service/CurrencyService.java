package org.adam.currency.service;

import org.adam.currency.domain.Currency;
import org.adam.currency.dto.CurrencyDTO;
import org.adam.currency.dto.CurrencyResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Currency Service.
 */
public interface CurrencyService {
    String MSG_INVALID_RESPONSE = "Invalid response from the currency service. Please try later.";
    String MSG_SERVICE_UNAVAILABLE = "Currency service is currently unavailable. Please try later.";
    /**
     * Finds all currencies.
     * @return a list of all currencies.
     */
    List<Currency> findAll();

    /**
     *
     * @param from currency code of source currency.
     * @param to currency code to which the conversion is performed.
     * @param amount amount to be converted.
     * @param date the date for which the request is send.
     * @return web service response represented as Currency DTO.
     */
    CurrencyDTO convertCurrency(String from, String to, String amount, LocalDate date);
}
