
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Salajrawi
 */
public interface VendingMachineDao {
    Item addItem(String itemId, Item item);

    List<Item> getAllItems();

    List<String> getAllItemIds();

    Item getItem(String itemId);

    Item updateItem(String itemId, Item item);

    Item removeItem(String itemId);

    Map<String, Item> loadItemsFromFile() throws VendingMachinePersistenceException;

    void writeItemsToFile() throws VendingMachinePersistenceException;
}
