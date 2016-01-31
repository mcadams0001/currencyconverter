package org.adam.currency.service;

import org.adam.currency.domain.Currency;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
     *
     * @param user user performing the conversion.
     * @param from currency code of source currency.
     * @param to currency code to which the conversion is performed.
     * @param amount amount to be converted.
     * @param date the date for which the request is send.
     * @return web service response represented as Currency DTO.
     */
    CurrencyResponseDTO convertCurrency(User user, String from, String to, double amount, Optional<LocalDate> date);
}
