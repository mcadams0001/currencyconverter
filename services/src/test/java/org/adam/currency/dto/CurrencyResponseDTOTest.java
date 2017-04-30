package org.adam.currency.dto;

import org.adam.currency.builder.CurrencyDTOBuilder;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CurrencyResponseDTOTest {
    private CurrencyResponseDTO dto1 = new CurrencyDTOBuilder().withQuote(2.0).withResult(3.0).withSuccess(true).withTimestamp(LocalDateTime.of(2017,4,30,11,35,0)).build();
    private CurrencyResponseDTO dto2 = new CurrencyDTOBuilder().withQuote(2.0).withResult(3.0).withSuccess(true).withTimestamp(LocalDateTime.of(2017,4,30,11,35,0)).build();
    private CurrencyResponseDTO dto3 = new CurrencyDTOBuilder().withQuote(3.0).withResult(4.0).withSuccess(false).withTimestamp(LocalDateTime.of(2017,4,30,11,35,0)).withError("error").build();

    @Test
    public void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(dto1, dto2, dto3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(dto1, dto2, dto3);
    }

    @Test
    public void verifyToString() throws Exception {
        assertThat(dto1.toString(), equalTo("CurrencyResponseDTO{success=true, quote=2.0, result=3.0}" +
                ""));
    }
}