
package com.sal.vendingmachine.ui;

import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;

/**
 *
 * @author salajrawi
 */
public class VendingMachineView {
    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayVendingMachineWelcome() {
        io.print("WELCOME TO SARAH'S VENDING MACHINE!\n");
    }

    public void displayItemHeader() {
        io.print("No\tItem\t\tPrice");
        io.print("----------------------");
    }

    public void displayItem(Item item) {
        io.print(item.getItemId() + "\t" + item.getName() + "\t$" + item.getCost());
    }

    public BigDecimal promptUserMoneyInput() {
        return io.readBigDecimal("\nPlease add funds by entering a number (entering 5 = adding $5.00): ");
    }

    public String promptUserItemChoice() {
        return io.readString("\nPlease choose the item you want to buy by entering it's number: ");
    }

    public void displayUserChoiceOfItem(Item item) {
        io.print("You have chosen " + item.getName() + ".");
        io.print("---------------------------------------");
    }

    public void displayUserMoneyInput(BigDecimal amount) {
        io.print("You have deposited: $" + amount + ".");
        io.print("---------------------------------------");
    }

    public void displayChangeDue(Change change) {
        io.print("Your change will be vended to you as: ");
        io.print("Quarters ($" + Coins.QUARTER.getValue() + "): " + change.getQuarters());
        io.print("Dimes ($" + Coins.DIME.getValue() + ") : " + change.getDimes());
        io.print("Nickels ($" + Coins.NICKEL.getValue() + ") : " + change.getNickels());
        io.print("Pennies ($" + Coins.PENNY.getValue() + ") : " + change.getPennies());
    }

    public void displayErrorMessage(String message) {
        io.print("==========ERROR==========");
        io.print(message);
        io.print("=========================");
    }
    public boolean toExit() {
        String answer = io.readString("Do you want to exit the Vending machine? (y/n)").toLowerCase();
        if (answer.startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }
    public void displayFinalMessage() {
        io.print("THANK YOU FOR YOUR PURCHASE!");
    }
    public void displayUserResponse() {
        io.print("Would you like to make another selection? y/n");
    }

    public void displayFinalGoodbyeMessage() {
        io.print("GOOD BYE!");
    }
}
