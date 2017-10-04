package org.adam.currency.helper;

import org.adam.currency.domain.History;
import org.adam.currency.dto.HistoryDTO;
import org.adam.currency.fixture.HistoryFixture;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryTransformerTest {

    private HistoryTransformer transformer = new HistoryTransformer();

    @Test
    void testTransform() throws Exception {
        History history = HistoryFixture.GBP_EUR_2016_1_30;
        HistoryDTO dto = transformer.apply(history);
        assertThat(dto, notNullValue());
        assertEquals(history.getCurrencyFrom().getCode(), dto.getCurrencyFrom().getCode());
        assertEquals(history.getCurrencyTo().getCode(), dto.getCurrencyTo().getCode());
        assertEquals(history.getTimeStamp(), dto.getTimeStamp());
        assertEquals(history.getResult(), dto.getResult());
        assertEquals(history.getRate(), dto.getRate());
        assertEquals(history.getAmount(), dto.getAmount());
        assertEquals(history.getDate(), dto.getDate());
        assertEquals(history.getId(), dto.getId());
    }

    @Test
    void shouldTransformNullToDtoInstance() throws Exception {
        assertThat(transformer.apply(null), notNullValue());
    }

}