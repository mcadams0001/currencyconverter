package org.adam.currency.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.common.CallTypeEnum;
import org.adam.currency.common.SettingField;
import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.helper.DateHelper;
import org.adam.currency.helper.ResponseTransformer;
import org.adam.currency.repository.GenericRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
    public static final Logger LOGGER = Logger.getLogger(CurrencyServiceImpl.class);


    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Currency> findAll() {
        return genericRepository.findAll(Currency.class, "name");
    }

    @Override
    public CurrencyResponseDTO convertCurrency(User user, String from, String to, String amount, LocalDate date) {
        Currency currencyFrom = getCurrencyByCode(from);
        Currency currencyTo = getCurrencyByCode(to);
        CurrencyResponse response = getResultFromDatabase(amount, date, currencyFrom, currencyTo);
        if (response == null) {
            response = getResultsFromWebService(amount, date, currencyFrom, currencyTo);
        }
        return new ResponseTransformer().transform(response);

    }

    private CurrencyResponse getResultsFromWebService(String amount, LocalDate date, Currency currencyFrom, Currency currencyTo) {
        CurrencyResponse response;
        response = invokeService(currencyFrom.getCode(), currencyTo.getCode(), amount, date);
        if (response != null && response.getSuccess()) {
            historyService.saveHistory(currencyFrom, currencyTo, amount, date, response, CallTypeEnum.WEB_SERVICE);
        }
        return response;
    }

    private CurrencyResponse getResultFromDatabase(String amount, LocalDate date, Currency currencyFrom, Currency currencyTo) {
        if (!isPastDate(date)) {
            return null;
        }
        History history = historyService.findBy(currencyFrom, currencyTo, date);
        if (history != null) {
            CurrencyResponse response = new CurrencyResponseBuilder().withResult(history.getRate() * Double.valueOf(amount)).withQuote(history.getRate()).withTimestamp(history.getTimeStamp()).build();
            historyService.saveHistory(currencyFrom, currencyTo, amount, date, response, CallTypeEnum.DATABASE);
            return response;
        }
        return null;
    }

    private boolean isPastDate(LocalDate date) {
        return date.isBefore(date.minusDays(1));
    }

    CurrencyResponse invokeService(String from, String to, String amount, LocalDate date) {
        try {
            String response = restTemplate.getForObject(getUrl(from, to, amount, date), String.class);
            return parseResponse(response);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            return CurrencyResponse.createError(MSG_SERVICE_UNAVAILABLE);
        }
    }

    private Currency getCurrencyByCode(String code) {
        return genericRepository.findById(Currency.class, code);
    }


    String getUrl(String from, String to, String amount, LocalDate date) {
        String serviceUrl = settingService.getSetting(SettingField.CURRENCY_SERVICE_URL);
        String accessKey = settingService.getSetting(SettingField.ACCESS_KEY);
        return String.format(serviceUrl, accessKey, from, to, amount, DateHelper.dateToString(date));
    }

    CurrencyResponse parseResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, CurrencyResponse.class);
        } catch (IOException e) {
            LOGGER.error("Failed to create parse results with message:" + e.getMessage(), e);
            LOGGER.error(response);
            return CurrencyResponse.createError(MSG_INVALID_RESPONSE);
        }
    }
}
