package org.adam.currency.helper;

import org.adam.currency.domain.History;
import org.adam.currency.dto.HistoryDTO;
import org.adam.currency.fixture.HistoryFixture;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class HistoryTransformerTest {

    private HistoryTransformer transformer = new HistoryTransformer();

    @Test
    public void testTransform() throws Exception {
        History history = HistoryFixture.GBP_EUR_2016_1_30;
        HistoryDTO dto = transformer.apply(history);
        assertThat(dto, notNullValue());
        assertThat(dto.getCurrencyFrom().getCode(), equalTo(history.getCurrencyFrom().getCode()));
        assertThat(dto.getCurrencyTo().getCode(), equalTo(history.getCurrencyTo().getCode()));
        assertThat(dto.getTimeStamp(), equalTo(history.getTimeStamp()));
        assertThat(dto.getResult(), equalTo(history.getResult()));
        assertThat(dto.getRate(), equalTo(history.getRate()));
        assertThat(dto.getAmount(), equalTo(history.getAmount()));
        assertThat(dto.getDate(), equalTo(history.getDate()));
        assertThat(dto.getId(), equalTo(history.getId()));
    }

    @Test
    public void shouldTransformNullToDtoInstance() throws Exception {
        assertThat(transformer.apply(null), notNullValue());
    }

}