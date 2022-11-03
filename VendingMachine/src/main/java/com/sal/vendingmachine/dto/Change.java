
package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 *
 * @author salajrawi
 */
public class Change {
    private HashMap<Coins,Integer> coinChangeMap=new HashMap<>();

    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    public Change(BigDecimal amount) {
        this.quarters = amount.divide(new BigDecimal("25")).intValue();
        amount = amount.remainder(new BigDecimal("25"));
        this.dimes = amount.divide(new BigDecimal("10")).intValue();
        amount = amount.remainder(new BigDecimal("10"));
        this.nickels = amount.divide(new BigDecimal("5")).intValue();
        amount = amount.remainder(new BigDecimal("5"));
        this.pennies = amount.divide(new BigDecimal("1")).intValue();
        amount = amount.remainder(new BigDecimal("1"));
    }

    public int getQuarters() { return quarters; }
    public int getDimes() { return dimes; }
    public int getNickels() { return nickels; }
    public int getPennies() { return pennies; }

}
