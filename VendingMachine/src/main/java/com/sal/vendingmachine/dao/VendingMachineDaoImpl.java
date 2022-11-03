
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineDaoImpl implements VendingMachineDao{

    public final String ITEMS_FILE;
    private Map<String, Item> items = new HashMap<>();
    public static final String DELIMITER = "::";
    public VendingMachineDaoImpl() { ITEMS_FILE = "items.txt"; }

    public VendingMachineDaoImpl(String itemsTextFile) {
        ITEMS_FILE = itemsTextFile;
    }

    @Override
    public Item addItem(String itemId, Item item) {
        Item prevItem = items.put(itemId, item);
        return prevItem;
    }

    @Override
    public List<Item> getAllItems() { return new ArrayList<Item>(items.values()); }

    @Override
    public List<String> getAllItemIds() { return new ArrayList<>(items.keySet()); }

    @Override
    public Item getItem(String itemId) {
        return items.get(itemId);
    }

    @Override
    public Item updateItem(String itemId, Item item) {
        return items.replace(itemId, item);
    }

    @Override
    public Item removeItem(String itemId) {
        Item removedItem = items.remove(itemId);
        return removedItem;
    }

    @Override
    public Map<String, Item> loadItemsFromFile() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ITEMS_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load data into memory", e);
        }
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = new Item(currentLine);
            items.put(currentItem.getItemId(), currentItem);
        }
        scanner.close();
        return items;
    }

    @Override
    public void writeItemsToFile() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ITEMS_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save item data", e);
        }
        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem: itemList) {
            itemAsText = marshallItemAsText(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    private String marshallItemAsText(Item item) {
        String itemAsText = item.getItemId() + DELIMITER;
        itemAsText += item.getName() + DELIMITER;
        itemAsText += item.getCost() + DELIMITER;
        itemAsText += item.getNumInventoryItems() + DELIMITER;
        return itemAsText;
    }

    private Item unmarshallItem(String itemAsText) {
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemId = itemTokens[0];

        Item itemFromFile;
        try {
            itemFromFile = new Item(itemId);
        } catch (VendingMachinePersistenceException e) {
            throw new RuntimeException(e);
        }

        itemFromFile.setName(itemTokens[1]);
        BigDecimal b = new BigDecimal(itemTokens[2]);
        itemFromFile.setCost(b);
        itemFromFile.setNumInventoryItems(Integer.parseInt(itemTokens[3]));

        return itemFromFile;
    }
}
