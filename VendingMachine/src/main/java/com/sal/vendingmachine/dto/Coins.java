
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author salajrawi
 */
public enum Coins {
    PENNY(new BigDecimal(0.01)),
    NICKEL(new BigDecimal(0.05)),
    DIME(new BigDecimal(0.1)),
    QUARTER(new BigDecimal(0.25));

    private final BigDecimal value;

    private Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue()
    {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
