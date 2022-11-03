
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.valueOf;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        //implement
        this.view = view;
        this.service = service;
    }
    
    public void run() throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException {
        BigDecimal moneyDeposited = new BigDecimal(0.0);
        Item chosenItem = null;
        String keepGoing = "yes";
        String input;
        Scanner scan = new Scanner(System.in);
        while (keepGoing.startsWith("y")) {
            boolean isEnoughMoney = false;
            try {
                displayWelcomeBanner();
                do {
                    productMenu();
                    moneyDeposited = userMoneyInput(moneyDeposited);
                    chosenItem = getChosenItem();
                    isEnoughMoney = didUserPutSufficientAmountOfMoney(moneyDeposited, chosenItem);
                    if (toExitVendingMachine(isEnoughMoney)) {
                        return;
                    }
                } while (!isEnoughMoney);
                displayUserMoneyInput(moneyDeposited);
                displayChangeReturnedToUser(moneyDeposited, chosenItem);
                updateSoldProduct(chosenItem);
                saveItemList();
            } catch (VendingMachineException | VendingMachineNoItemInventoryException ex) {
                view.displayErrorMessage(ex.getMessage());
            } finally {
                displayFinalMessage();
            }
            view.displayUserResponse();
            keepGoing = scan.nextLine().toLowerCase();
        }
    }


    public void displayWelcomeBanner() {
        view.displayVendingMachineWelcome();
    }
    public void productMenu() throws VendingMachineException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        view.displayItemHeader();
        Map<String, Item> Items = service.loadItemsInStock();
        for (Item i: Items.values()) {
            view.displayItem(i);
        }
    }
    public BigDecimal userMoneyInput(BigDecimal amount) {
        return amount.add(view.promptUserMoneyInput());
    }
    public Item getChosenItem() {
        while(true) {
            String itemId = view.promptUserItemChoice();
            try {
                Item item = service.getChosenItem(itemId);
                view.displayUserChoiceOfItem(item);
                return item;
            } catch (VendingMachineNoItemInventoryException ex) {
                displayErrorMessage(ex.getMessage());
            }
        }
    }
    boolean didUserPutSufficientAmountOfMoney(BigDecimal moneyDeposited, Item chosenItem)
            throws VendingMachineInsufficientFundsException,
            VendingMachineException,
            VendingMachinePersistenceException {
        try {
            service.checkSufficientMoneyToBuyProduct(moneyDeposited, chosenItem);
            return true;
        } catch (VendingMachineInsufficientFundsException ex){
            displayErrorMessage(ex.getMessage());
            displayUserMoneyInput(moneyDeposited);
            return false;
        }
    }
    void displayUserMoneyInput(BigDecimal amount) {
        view.displayUserMoneyInput(amount);
    }

    void updateSoldItem(Item chosenItem) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        service.updateItemSale(chosenItem);
    }

    void displayChangeReturnedToUser(BigDecimal moneyDeposited, Item item) {
        Change change = service.calculateChange(moneyDeposited, item);
        view.displayChangeDue(change);
    }
    private boolean toExitVendingMachine(boolean isEnoughMoney) {
        if (isEnoughMoney) {
            return false;
        } else {
            return view.toExit();
        }
    }
    void displayErrorMessage(String message) {
        view.displayErrorMessage(message);
    }
    void updateSoldProduct(Item item) throws VendingMachinePersistenceException {
        try {
            service.updateItemSale(item);
        } catch (VendingMachineNoItemInventoryException e) {
            throw new VendingMachinePersistenceException(e.getMessage());
        }
    }
    private void saveItemList() throws VendingMachinePersistenceException {
        service.saveItemList();
    }
    private void displayFinalMessage() {
        view.displayFinalMessage();
    }

}
