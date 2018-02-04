package org.adam.currency.helper;

import org.adam.currency.builder.CurrencyResponseBuilder;
import org.adam.currency.dto.CurrencyResponse;
import org.adam.currency.dto.CurrencyResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseTransformerTest {

    @Test
    void testTransform() {
        CurrencyResponse response = new CurrencyResponseBuilder().withTimestamp(LocalDateTime.of(2016, 1, 30, 19, 30, 15)).withQuote(0.6).withResult(200.0d).withSuccess(true).build();
        CurrencyResponseDTO currencyResponseDTO = new ResponseTransformer().apply(response);
        assertNotNull(currencyResponseDTO);
        assertEquals(response.getInfo().getQuote(), currencyResponseDTO.getQuote());
        assertEquals(response.getResult(), currencyResponseDTO.getResult());
        assertEquals(response.getInfo().getTimestamp(), currencyResponseDTO.getTimestamp());
        assertEquals(response.getError().getInfo(), currencyResponseDTO.getError());
        assertEquals(response.getSuccess(), currencyResponseDTO.isSuccess());
    }

    @Test
    void shouldTransformNullResponseToEmptyCurrencyDTO() throws Exception {
        assertEquals(new CurrencyResponseDTO(), new ResponseTransformer().apply(null));
    }

}