package org.adam.currency.dto;

import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

class CurrencyErrorTest {

    private CurrencyError error1 = new CurrencyError("GBP", "Error1");
    private CurrencyError error2 = new CurrencyError("GBP", "Error1");
    private CurrencyError error3 = new CurrencyError("EUR", "Error2");

    @Test
    void errorEquals() {
        error3.setCode("USD");
        EqualsTestHelper.verifyEquals(error1, error2, error3);
    }

    @Test
    void errorHashCode() {
        EqualsTestHelper.verifyHashCode(error1, error2, error3);

    }
}