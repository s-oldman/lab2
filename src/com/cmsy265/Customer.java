package com.cmsy265;

import java.math.BigDecimal;    // So we don't get floating-point errors when doing money math.
import java.math.RoundingMode;  // Used to set BigDecimal scale (how many digits after the decimal to round to, and how to round if we have more digita than that stored).
import java.util.ArrayList;     // For customer purchase history.

/**
 * @author Sam Young
 * @description Customer class file: a String, two ints, a double, and an ArrayList of TVs. (Plus some boilerplate.)
 * @version 1.1
 * @since 2025-02-21
 */

class Customer implements Constants {

    /**
     * @description Five private variables:
     *   * Customer name
     *   * Customer account number
     *   * The list of TVs the customer's buying
     *   * Number of TVs in said list
     *   * Cost of TVs in said list (includes sales tax)
     */
    private String        name;
    private int           acctNum;
    private ArrayList<TV> tvIDs;
    private int           tvsBought;
    private BigDecimal    tvsCost;

    /**
     * @description Default constructor. name is null, acctNum is 0.
     * @return Constructed Customer object.
     */
    public Customer() {
        this.name       = null;
        this.acctNum    = 0;
        this.tvIDs      = new ArrayList<TV>();
        this.tvsBought  = 0;
        this.tvsCost    = new BigDecimal("0");

    }

    /**
     * @description Full constructor.
     * @param String Customer name
     * @param int Customer account number
     * @param ArrayList<TV> ArrayList of TV objects bought
     * @return Constructed Customer object
     */
    public Customer(String name, int acctNum, ArrayList<TV> tvIDs) {
        this.name       = name;
        this.acctNum    = acctNum;
        this.tvIDs      = tvIDs;
        this.tvsBought  = tvIDs.size();
        this.tvsCost    = subtotal();
    }

    /**
     * @description Getter for name.
     * @return Customer name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @description Getter for acctNum.
     * @return Account number.
     */
    public int getAcctNum() {
        return this.acctNum;
    }

    /**
     * @description Getter for ArrayList.
     * @return ArrayList containing TV objects (one for each the customer's buying).
     */
    public ArrayList<TV> getTvIDs() {
        return this.tvIDs;
    }

    /**
     * @description Getter for tvsBought.
     * @return Number of TVs the customer's trying to buy.
     */
    public int getTvsBought() {
        return this.tvsBought;
    }

    /**
     * @description Getter for tvsCost.
     * @return Subtotal: tvsBought * TV_COST.
     */
    public BigDecimal getTvsCost() {
        return this.tvsCost;
    }

    /**
     * @description Setter for name.
     * @param newName Customer name.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * @description Setter for acctNum.
     * @param newAcctNum Customer account number.
     */
    public void setAcctNum(int newAcctNum) {
        this.acctNum = newAcctNum;
    }

    /**
     * @description Setter for ArrayList.
     * @param newList ArrayList containing TV objects (one for each the customer's buying).
     */
    public void setTvIDs(ArrayList<TV> newList) {
        this.tvIDs     = newList;
        this.tvsBought = newList.size();
        this.tvsCost   = subtotal();

    }

    /**
     * @description Add a TV to the customer's order.
     * @param tv The TV object to add.
     */
    public void addTv(TV tv) {
        this.tvIDs.addLast(tv);
        this.tvsBought = tvIDs.size();
        this.tvsCost   = subtotal();
    }

    /**
     * @description Remove a TV from the customer's order.
     */
    public void removeTv() {

        if (tvIDs.size() <= 0) {
            System.out.println("[E] No TVs in this customer's order, so we can't remove any.");
        } else {
            this.tvIDs.removeLast();
            this.tvsBought = tvIDs.size();
            this.tvsCost   = subtotal();
        }
    }

    /**
     * @description Contents of Customer object.
     * @return String containing customer info. (Formatted as a receipt, for use during checkout.)
     */
    public String toString() {

        String sb =  "Receipt for cusomter account " + this.getAcctNum() + ":" + System.lineSeparator();
               sb += System.lineSeparator();

               sb += "  Customer name: " + this.getName() + System.lineSeparator();
               sb += System.lineSeparator();

               sb += "  Number of TVs bought: " + this.getTvsBought() + System.lineSeparator();
               for (TV tv : this.getTvIDs()) {
                   sb += "    " + tv.get() + System.lineSeparator();
               }
               sb += System.lineSeparator();

               sb += "  Subtotal: $" + this.getTvsCost().toString() + System.lineSeparator();
               sb += "  Tax:      $" + this.tax().toString()        + System.lineSeparator();
               sb += System.lineSeparator();

               sb += "  Total:    $" + this.total().toString()      + System.lineSeparator();
               sb += System.lineSeparator();

        return sb;
    }

    /**
     * @description Calculate customer's sales subtotal.
     * @return BigDecimal containing subtotal (rounded to 2 decimal places).
     */
    private BigDecimal subtotal() {

        // Do the math first, then round to two decimal places.
        return TV_COST.multiply(new BigDecimal(this.tvsBought)).setScale(CENTS_DIGITS, RoundingMode.HALF_UP);

    }

    /**
     * @description Calculate customer's sales tax.
     * @return BigDecimal containing tax (rounded to 2 decimal places).
     */
    private BigDecimal tax() {

        // Do the math first, then round to two decimal places.
        return this.getTvsCost().multiply(SALES_TAX).setScale(CENTS_DIGITS, RoundingMode.HALF_UP);

    }

    /**
     * @description Calculate customer's sales tax.
     * @return BigDecimal containing tax (rounded to 2 decimal places).
     */
    private BigDecimal total() {

        // Do the math first, then round to two decimal places.
        return this.getTvsCost().add(this.tax()).setScale(CENTS_DIGITS, RoundingMode.HALF_UP);

    }

}
