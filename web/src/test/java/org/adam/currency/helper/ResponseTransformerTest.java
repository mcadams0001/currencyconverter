package org.adam.currency.helper;

import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.adam.currency.dto.CurrencyResponse;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ResponseTransformerTest {

    @Test
    public void testTransform() throws Exception {
        CurrencyResponse response = new CurrencyResponseBuilder().withTimestamp(LocalDateTime.of(2016,1,30, 19,30,15)).withQuote(0.6).withResult(200.0d).withSuccess(true).build();
        CurrencyResponseDTO currencyResponseDTO = new ResponseTransformer().transform(response);
        assertThat(currencyResponseDTO, notNullValue());
        assertThat(currencyResponseDTO.getQuote(), equalTo(response.getInfo().getQuote()));
        assertThat(currencyResponseDTO.getResult(), equalTo(response.getResult()));
        assertThat(currencyResponseDTO.getTimestamp(), equalTo(response.getInfo().getTimestamp()));
        assertThat(currencyResponseDTO.getError(), equalTo(response.getError().getInfo()));
        assertThat(currencyResponseDTO.isSuccess(), equalTo(response.getSuccess()));
    }

    @Test
    public void shouldTransformNullResponseToEmptyCurrencyDTO() throws Exception {
        assertThat(new ResponseTransformer().transform(null), equalTo(new CurrencyResponseDTO()));
    }

}