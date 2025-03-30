package com.cmsy265;

import java.io.BufferedReader;          // For reading files in chunks.
import java.io.FileNotFoundException;   // Required by FileReader.
import java.io.FileReader;              // For reading files.
import java.util.ArrayList;             // For customer purchase history.
import java.util.Iterator;              // For iterating through Stack<TV>.
import java.util.ArrayDeque;            // For the customer queue. (Much better than LinkedList, which requires
                                        //   linear time & invokes garbage collection just to enqueue a customer.
                                        //   And it implements the LinkedList interface, anyways.)
import java.util.Scanner;               // For reading user input.
import java.util.Stack;                 // For the store inventory.

/**
 * @author Sam Young
 * @duedate 2024-03-05
 * @description TV store Inventory management, now with customer Queues.
 * @version 1.1
 * @since 2025-02-21
 */
public class Driver implements Constants {

    /**
     * Variables:
     *   * A Scanner, to read from System.in.
     *   * Our inventory (a Stack of TVs).
     *   * An ArrayDeque, for the Customer queue.
     */
    private static Scanner              stdin     = new Scanner(System.in);
    private static Stack<TV>            inventory = new Stack<>();
    private static ArrayDeque<Customer> customers = new ArrayDeque<>();

    /**
     * @description The main event loop.
     */
    public static void main(String[] args) {

        displayHeader();

        int choice = 0;

        if (readInventory()) {

            while (true) {

                displayMenu();

                choice = promptForMenuOption();

                processMenuOption(choice);

            }

        } else {

            System.out.println("[C] File \"" + fileName + "\" not found in classpath, so we can't initialize the inventory list. Exiting.");
            System.exit(1);
        }

    }

    /**
     * @description Display program header & copyright.
     */
    private static void displayHeader() {
        System.out.println("                       CMSY-265 Lab 2");
        System.out.println("Copyright ©2025 Howard Community College. All rights reserved; unauthorized duplication prohibited.");
        System.out.println("        Welcome to the CMSY-265 TV Inventory Management Program (part 2)");
    }

    /**
     * @description Read inventory data from fileName.
     * @return Whether or not we were able to read the data.
     */
    public static boolean readInventory() {

        Scanner filein = null;
    
        try {
            filein = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            return false;
        }

        // File's open, now iterate through it.
        while (filein.hasNextLine()) {
            String id = filein.nextLine();
            inventory.push(new TV(id));
        }

        filein.close();
        return true;

    }

    /**
     * @description Display menu options.
     */
    private static void displayMenu() {
        System.out.println("");
        System.out.println("");
        System.out.println("TV Inventory Management Menu:");
        System.out.println("  " + STOCK_SHELVES     + ". Stock Shelves");
        System.out.println("  " + FILL_WEB_ORDER    + ". Fill Web Order");
        System.out.println("  " + RESTOCK_RETURN    + ". Restock Returned TV");
        System.out.println("  " + RESTOCK_INVENTORY + ". Restock Inventory");
        System.out.println("  " + DISPLAY_INVENTORY + ". Display Inventory");
        System.out.println("  " + CUSTOMER_PURCHASE + ". Customer Purchase");
        System.out.println("  " + CUSTOMER_CHECKOUT + ". Customer Checkout");
        System.out.println("  " + END_PROGRAM       + ". End Program");
    }

    /**
     * @description Prompt for valid menu option using stdin.
     * @return valid menu option number
     */
    private static int promptForMenuOption() {

        int option = 0;

        do {

            System.out.println("");
            System.out.print("Option: ");

            String userInput = stdin.nextLine();

            try {

                option = Integer.parseInt(userInput);

            } catch (NumberFormatException e) {

                continue;

            }

            System.out.println("");

        } while (!isValidMenuOption(option, STOCK_SHELVES, END_PROGRAM));

        return option;

    }

    /**
     * @description Check if the menu option the user provided is valid.
     * @param option Menu option parsed from user input.
     * @param min Lowest allowable menu option.
     * @param max Highest allowable menu option.
     * @return Is it valid?
     */
    public static boolean isValidMenuOption(int option, int min, int max) {

        if (option < min || option > max) {

            System.out.println("[E] Invalid selection. Try again.");
            return false;

        } else if (option == STOCK_SHELVES && inventory.size() <= STOCK_AMT) {

            System.out.println("[E] Only " + inventory.size() + " TVs in inventory, and we need at least " + STOCK_AMT + " to stock the shelves. Please select another option.");
            return false;

        } else if (option == FILL_WEB_ORDER && inventory.size() <= 0) {

            System.out.println("[E] No TVs remaining in inventory, so we can't fill any web orders. Restock and try again.");
            return false;

        } else if (option == CUSTOMER_PURCHASE && inventory.size() <= 0) {

            System.out.println("[E] No TVs currently in inventory, so we can't sell any to customers. Restock and try again.");
            return false;

        } else if (option == CUSTOMER_CHECKOUT && customers.size() <= 0) {

            System.out.println("[W] No customers to checkout right now.");
            return false;

        } else if (option == DISPLAY_INVENTORY && inventory.size() <= 0) {

            System.out.println("[I] No TVs currently in inventory.");
            return false;

        } else if (option == END_PROGRAM && customers.size() > 0) {

            System.out.println("[E] There are still " + customers.size() + " customers in the checkout queue. Finish checking them out, and then we can stop.");
            return false;

        } else return true;

    }

    /**
     * @description Do the thing the user picked.
     * @param option Menu option, validated from user input.
     */
    private static void processMenuOption(int option) {

        switch (option) {

            case STOCK_SHELVES:
                stockShelves();
                break;
    
            case FILL_WEB_ORDER:
                fillWebOrder();
                break;

            case RESTOCK_RETURN:
                restockReturn();
                break;

            case RESTOCK_INVENTORY:
                restockInventory();
                break;

            case DISPLAY_INVENTORY:
                displayInventory(inventory);
                break;

            case CUSTOMER_PURCHASE:
                customerPurchase(inventory, customers);
                break;

            case CUSTOMER_CHECKOUT:
                customerCheckout(customers);
                break;

            case END_PROGRAM:
                endProgram();
                break;

        }

    }

    /**
     * @description Menu option 1: Stock shelves (i.e.: remove five TVs from inventory).
     */
    private static void stockShelves() {

        System.out.println("");
        System.out.println("Stocked the following TVs onto the shelves:");

        for (int i = 0; i < STOCK_AMT; i++) {

            TV tv = inventory.pop();
            System.out.println(tv);

        }

    }

    /**
     * @description Menu option 2: Fill web order (i.e.: remove one TV from inventory).
     */
    private static void fillWebOrder() {

        TV tv = inventory.pop();

        System.out.println("");
        System.out.println("Filled the following TV as a web order:");
        System.out.println(tv);

    }

    /**
     * @description Menu option 3: Restock one TV into inventory.
     */
    private static void restockReturn() {

        if (!(inventory.size() <= 0)) {
            inventory.push(new TV(TV.nextId(inventory.peek().get())));
        } else {
            inventory.push(new TV("ABC123-0"));
        }

        System.out.println("");
        System.out.println("Added the following TV as a return: " + inventory.peek().get());

    }

    /**
     * @description Menu option 4: Restock five TVs into inventory.
     */
    private static void restockInventory() {

        System.out.println("");
        System.out.println("Added the following TVs as restocks:");

        for (int i = 0; i < RESTOCK_AMT; i++) {

            if (!(inventory.size() <= 0)) {

                inventory.push(new TV(TV.nextId(inventory.peek().get())));
                System.out.println(inventory.peek().get());

            } else {

                inventory.push(new TV("ABC123-0"));
                System.out.println(inventory.peek().get());

            }

        }

    }

    /**
     * @description Menu option 6: Buy something. (Or several somethings.)
     * @param inventory Stack of TVs currently in inventory.
     * @param customers Deque to add customer to the end of.
     */
    private static void customerPurchase(Stack<TV> inventory, ArrayDeque<Customer> customers) {

        // These will prompt forever, until the user types in something valid.
        String  acctName = promptForString("Customer name: ");
        int     acctNum  = promptForInt("Customer account number: ", MIN_ACCT_NUM, MAX_ACCT_NUM);
        int     tvsCount = promptForInt("Number of TVs they're buying: ", MIN_TVSTOBUY, inventory.size());

        ArrayList<TV> tvsToBuy = new ArrayList<TV>();

        for (int i = 0; i < tvsCount; i++) {
            tvsToBuy.add(inventory.pop());
        }
        Customer c = new Customer(acctName, acctNum, tvsToBuy);

        System.out.println("There are " + inventory.size() + " more TVs left in inventory.");

        customers.addLast(c);

    }

    /**
     * @description Ask for any String from the user.
     * @param prompt to show user, before asking.
     * @return The String, taken from user input.
     */
    private static String promptForString(String prompt) {
        System.out.print(prompt);
        return stdin.nextLine();
    }

    /**
     * @description Ask for a valid int from the user. (Reprompts if invalid.)
     * @param prompt to show user, before asking.
     * @return The vaildated int, taken from user input.
     */
    private static int promptForInt(String prompt, int min, int max) {

        // Variables:
        String in = "";   // User input, unparsed.
        int input = -1;   // int to validate.

        while (true) {

            System.out.print(prompt);
            in = stdin.nextLine();

            try {

                input = Integer.parseInt(in);

            } catch (NumberFormatException e) {

                // Cast failed because an int couldn't be parsed from input.
                System.out.println("[E] That's not an integer. Try again.");
                System.out.println("");

            }

            // Time for custom validation.
            if (validate(input, min, max)) {
                return input;
            }

        }

    }

    /**
     * @description Custom validation for ints: min <= input <= max.
     * @param input The int to validate.
     * @param min Minimum allowable int.
     * @param max Maximum allowable int.
     * @return Validated int.
     */
    private static boolean validate(int input, int min, int max) {

        if (input < min) {

            System.out.println("[E] That's too small (lowest allowable is " + min + "). Try again.");
            System.out.println("");
            return false;

        } else if (input > max) {

            System.out.println("[E] That's too big (highest allowable is " + max + "). Try again.");
            System.out.println("");
            return false;

        } else {

            return true;

        }

    }

    /**
     * @description Menu option 7: Check out the next customer in the queue.
     */
    private static void customerCheckout(ArrayDeque<Customer> customers) {

        Customer checkout = customers.removeFirst();

        System.out.println(checkout);

        if (customers.size() == 0) {

            System.out.println("No more customers in the queue.");

        } else if (customers.size() == 1) {

            System.out.println("Only " + customers.size() + " more customer in the queue.");

        } else {

            System.out.println("There are " + customers.size() + " more customers in the queue.");

        }

    }

    /**
     * @description Menu option 7: Print contents of stack. (Uses Stack.Iterator().)
     */
    private static void displayInventory(Stack<TV> inventory) {

        System.out.println("");
        System.out.println("Current inventory:");

        Iterator<TV> iterator = inventory.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

    /**
     * @description Are we done here? Let's check.
     */
    private static void endProgram() {

        displayInventory(inventory);

        System.out.println("");
        System.out.println("");
        System.out.println("Thanks for using the program. Exiting.");
        System.exit(0);

    }

}
