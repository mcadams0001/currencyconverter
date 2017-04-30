package org.adam.currency.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.helper.CurrencyTransformer;
import org.adam.currency.helper.DateHelper;
import org.adam.currency.helper.ResponseTransformer;
import org.adam.currency.repository.GenericRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
    static final String MSG_INVALID_RESPONSE = "Invalid response from the currency service. Please try later.";
    static final String MSG_SERVICE_UNAVAILABLE = "Currency service is currently unavailable. Please try later.";
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);


    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("#{systemProperties['ACCESS_KEY']}")
    private String accessKey;

    @Override
    public List<Currency> findAll() {
        return genericRepository.findAll(Currency.class, "name");
    }

    @Override
    public CurrencyResponseDTO convertCurrency(User user, String from, String to, double amount, Optional<LocalDate> selectedDate) {
        Currency currencyFrom = getCurrencyByCode(from);
        Currency currencyTo = getCurrencyByCode(to);
        LocalDate date = selectedDate.orElse(LocalDate.now());
        CurrencyResponse response = getResultForPastDaysFromDatabase(user, amount, date, currencyFrom, currencyTo);
        if (response == null) {
            response = getResultsFromWebService(user, amount, currencyFrom, currencyTo);
        }
        return toCurrencyResponseDTO(response, amount, currencyFrom, currencyTo);

    }

    CurrencyResponseDTO toCurrencyResponseDTO(CurrencyResponse response, double amount, Currency currencyFrom, Currency currencyTo) {
        CurrencyTransformer currencyTransformer = new CurrencyTransformer();
        CurrencyResponseDTO dto = new ResponseTransformer().apply(response);
        dto.setAmount(amount);
        dto.setCurrencyFrom(currencyTransformer.apply(currencyFrom));
        dto.setCurrencyTo(currencyTransformer.apply(currencyTo));
        return dto;
    }

    private CurrencyResponse getResultsFromWebService(User user, double amount, Currency currencyFrom, Currency currencyTo) {
        History history = historyService.findRecent(currencyFrom, currencyTo);
        if (history != null) {
            return createResponseFromHistory(user, amount, LocalDate.now(), currencyFrom, currencyTo, history);
        }
        CurrencyResponse response = invokeService(currencyFrom.getCode(), currencyTo.getCode(), amount);
        if (response != null && response.getSuccess()) {
            historyService.saveHistory(user, currencyFrom, currencyTo, amount, LocalDate.now(), response, CallTypeEnum.WEB_SERVICE);
        }
        return response;
    }

    CurrencyResponse getResultForPastDaysFromDatabase(User user, double amount, LocalDate date, Currency currencyFrom, Currency currencyTo) {
        if (!isPastDate(date)) {
            return null;
        }
        History history = historyService.findBy(currencyFrom, currencyTo, date);
        if (history != null) {
            return createResponseFromHistory(user, amount, date, currencyFrom, currencyTo, history);
        }
        return CurrencyResponse.createError("Historical exchange rate for " + currencyFrom.getCode() + " and " + currencyTo.getCode() + " for " + DateHelper.localDateToAppString(date) + " is not available");
    }

    private CurrencyResponse createResponseFromHistory(User user, double amount, LocalDate date, Currency currencyFrom, Currency currencyTo, History history) {
        CurrencyResponse response = new CurrencyResponseBuilder().withResult(history.getRate() * amount).withQuote(history.getRate()).withTimestamp(history.getTimeStamp()).build();
        historyService.saveHistory(user, currencyFrom, currencyTo, amount, date, response, CallTypeEnum.DATABASE);
        return response;
    }

    private boolean isPastDate(LocalDate date) {
        return date == null || date.isBefore(LocalDate.now());
    }

    CurrencyResponse invokeService(String from, String to, double amount) {
        try {
            String response = restTemplate.getForObject(getUrl(from, to), String.class);
            return parseResponse(response, from, to, amount);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            return CurrencyResponse.createError(MSG_SERVICE_UNAVAILABLE);
        }
    }

    private Currency getCurrencyByCode(String code) {
        return genericRepository.findById(Currency.class, code);
    }


    String getUrl(String from, String to) {
        String serviceUrl = settingService.getSetting(SettingField.CURRENCY_SERVICE_URL);
        return String.format(serviceUrl, accessKey, from + "," + to);
    }

    @SuppressWarnings("unchecked")
    CurrencyResponse parseResponse(String response, String currencyFrom, String currencyTo, double amount) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(response, HashMap.class);
            Boolean success = (Boolean) map.get("success");
            if (!Boolean.TRUE.equals(success)) {
                return failureResponse(map);
            }
            return successResponse(currencyFrom, currencyTo, amount, map);
        } catch (IOException e) {
            LOGGER.error("Failed to create parse results with message:" + e.getMessage(), e);
            LOGGER.error(response);
            return CurrencyResponse.createError(MSG_INVALID_RESPONSE);
        }
    }

    private CurrencyResponse failureResponse(Map<String, Object> map) {
        Map<String, String> error = (Map<String, String>) map.get("error");
        return new CurrencyResponseBuilder().withSuccess(false).withErrorMessage(error.get("info")).build();
    }

    private CurrencyResponse successResponse(String currencyFrom, String currencyTo, double amount, Map<String, Object> map) {
        Map<String, Double> quotes = (Map<String, Double>) map.get("quotes");
        double quote = getQuote(quotes, currencyFrom, currencyTo);
        return new CurrencyResponseBuilder()
                .withSuccess(true)
                .withTimestamp(DateHelper.timestampToLocalDateTime((Integer) map.get("timestamp")))
                .withQuote(quote)
                .withResult(calculateResult(amount, quote))
                .build();
    }

    double getQuote(Map<String, Double> quotes, String currencyFrom, String currencyTo) {
        if (!isUSD(currencyFrom) && !isUSD(currencyTo)) {
            return new BigDecimal(quotes.get("USD" + currencyTo)).divide(new BigDecimal(quotes.get("USD" + currencyFrom)), 9, BigDecimal.ROUND_CEILING).doubleValue();
        } else if (isUSD(currencyFrom) && isUSD(currencyTo)) {
            return 1.0d;
        } else if (isUSD(currencyFrom)) {
            return quotes.get("USD" + currencyTo);
        } else if (isUSD(currencyTo)) {
            return 1.0 / quotes.get("USD" + currencyFrom);
        }
        return 0.0d;
    }

    private boolean isUSD(String currencyFrom) {
        return "USD".equalsIgnoreCase(currencyFrom);
    }


    double calculateResult(Double amount, Double quote) {
        return new BigDecimal(amount).multiply(new BigDecimal(quote)).doubleValue();
    }
}
