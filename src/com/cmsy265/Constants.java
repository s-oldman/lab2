package com.cmsy265;

import java.math.BigDecimal;    // So we don't get floating-point errors when doing basic money math.

/**
 * @author Sam Young
 * @description Interface implemented by all classes (including the driver). (Only contains program constants, no methods!)
 * @version 1.1
 * @since 2025-02-21
 */
public interface Constants {

    /**
     * Menu options.
     */
    final int STOCK_SHELVES     = 1;
    final int FILL_WEB_ORDER    = 2;
    final int RESTOCK_RETURN    = 3;
    final int RESTOCK_INVENTORY = 4;
    final int DISPLAY_INVENTORY = 5;
    final int CUSTOMER_PURCHASE = 6;
    final int CUSTOMER_CHECKOUT = 7;
    final int END_PROGRAM       = 8;

    /**
     * How many TVs to push() or pop() when using menu options
     * 1 or 4, respectively.
     */
    final int STOCK_AMT   = 5;
    final int RESTOCK_AMT = 5;

    /**
     * Filename containing list of TV IDs (plaintext). Relative to classpath root.
     */
    final String fileName = "txt/stack.txt";

    /**
     * Constants for calculating cost.
     */
    final int        CENTS_DIGITS = 2;
    final BigDecimal TV_COST      = new BigDecimal("199.95");
    final BigDecimal SALES_TAX    = new BigDecimal("0.06");

    /**
     * Constants for validating ints (acctNum and tvsToBuy).
     */
    final int MIN_TVSTOBUY = 1;
    final int MIN_ACCT_NUM = 1;
    final int MAX_ACCT_NUM = 999999;

}
