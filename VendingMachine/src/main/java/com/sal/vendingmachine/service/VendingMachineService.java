
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public interface VendingMachineService {

    public Map<String, Item> loadItemsInStock() throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException, VendingMachineException;

    void saveItemList() throws VendingMachinePersistenceException;

    public Item getChosenItem(String itemId) throws VendingMachineNoItemInventoryException;

    public void checkSufficientMoneyToBuyProduct(BigDecimal inputAmount, Item item) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException;

    public Change calculateChange(BigDecimal amount, Item item);

    public void updateItemSale(Item item) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException;
}
