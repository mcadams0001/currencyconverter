package org.adam.currency.helper;

import org.adam.currency.domain.Currency;
import org.adam.currency.dto.CurrencyDTO;
import org.adam.currency.fixture.CurrencyFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyTransformerTest {

    @Test
    void testTransform() {
        CurrencyTransformer transformer = new CurrencyTransformer();
        Currency currency = CurrencyFixture.GBP;
        CurrencyDTO dto = transformer.apply(currency);
        assertNotNull(dto);
        assertEquals(currency.getCode(), dto.getCode());
        assertEquals(currency.getName(), dto.getName());
        assertEquals(currency.getCountry().getName(), dto.getCountry());
    }

    @Test
    void shouldTransformNullToDtoInstance() {
        CurrencyTransformer transformer = new CurrencyTransformer();
        CurrencyDTO dto = transformer.apply(null);
        assertNotNull(dto);
    }

}