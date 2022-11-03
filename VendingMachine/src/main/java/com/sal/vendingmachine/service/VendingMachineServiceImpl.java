
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachineDao dao;
    private AuditDao auditDao;

    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao auditDao) {
        //implement
        this.dao = dao;
        this.auditDao = auditDao;

    }

    @Override
    public Map<String, Item> loadItemsInStock() throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException, VendingMachineException {
        Map<String, Item> itemsInStock = new HashMap<>();
        for (Item i: dao.loadItemsFromFile().values()) {
            if (i.getNumInventoryItems() < 1) {
                auditDao.writeAuditEntry("Product Id: " + i.getItemId() + " quantity in stock is zero.");
            } else {
                itemsInStock.put(i.getItemId(), i);
            }
        }
        return itemsInStock;
    }

    @Override
    public void saveItemList() throws VendingMachinePersistenceException {
        dao.writeItemsToFile();
        auditDao.writeAuditEntry("Item list saved to file");
    }

    @Override
    public Item getChosenItem(String itemId) throws VendingMachineNoItemInventoryException {
        validateItemsInStock(itemId);
        return dao.getItem(itemId);
    }

    @Override
    public void checkSufficientMoneyToBuyProduct(BigDecimal inputAmount, Item item) throws VendingMachineInsufficientFundsException {
        if (inputAmount.compareTo(item.getCost()) < 0) {
            throw new VendingMachineInsufficientFundsException("Insufficient funds to buy " + item.getName());
        }
    }

    @Override
    public Change calculateChange(BigDecimal amount, Item item) {
        BigDecimal change = amount.subtract(item.getCost()).multiply(new BigDecimal("100"));
        return new Change(change);
    }

    @Override
    public void updateItemSale(Item item) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        if (item.getNumInventoryItems() > 0) {
            item.setNumInventoryItems(item.getNumInventoryItems() - 1);
        } else {
            throw new VendingMachineNoItemInventoryException("Selected item is not in stock");
        }
        dao.updateItem(item.getItemId(), item);
        auditDao.writeAuditEntry("Item Id: " + item.getItemId() + " is updated.");
    }

    private void validateItemsInStock(String itemId) throws VendingMachineNoItemInventoryException {
        List<String> ids = dao.getAllItemIds();
        Item item = dao.getItem(itemId);
        if (!ids.contains(itemId) || (item.getNumInventoryItems() < 1)) {
            throw new VendingMachineNoItemInventoryException("Selected item is not in stock.");
        }
    }
}
