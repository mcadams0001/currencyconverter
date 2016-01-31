package org.adam.currency.helper;

import org.adam.currency.domain.Currency;
import org.adam.currency.dto.CurrencyDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class CurrencyTransformerTest {

    @Test
    public void testTransform() throws Exception {
        CurrencyTransformer transformer = new CurrencyTransformer();
        Currency currency = CurrencyFixture.GBP;
        CurrencyDTO dto = transformer.transform(currency);
        assertThat(dto, notNullValue());
        assertThat(dto.getCode(), equalTo(currency.getCode()));
        assertThat(dto.getName(), equalTo(currency.getName()));
        assertThat(dto.getCountry(), equalTo(currency.getCountry().getName()));
    }

    @Test
    public void shouldTransformNullToDtoInstance() throws Exception {
        CurrencyTransformer transformer = new CurrencyTransformer();
        CurrencyDTO dto = transformer.transform(null);
        assertThat(dto, notNullValue());
    }

}