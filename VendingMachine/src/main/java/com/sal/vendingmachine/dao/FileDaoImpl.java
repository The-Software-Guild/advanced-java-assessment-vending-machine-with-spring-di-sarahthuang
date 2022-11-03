
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.FileDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salajrawi
 */
public class FileDaoImpl implements FileDao {

    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";
    private Map<String, Item> itemMap = new HashMap<>();
    public Item unmarshallItem(String line) {
        //implement
        String[] itemTokens = line.split(DELIMITER);
        String itemId = itemTokens[0];
        String itemName = itemTokens[1];
        BigDecimal itemCost = new BigDecimal(itemTokens[2]);
        itemCost.setScale(2, RoundingMode.HALF_DOWN);
        int itemNumInventory = Integer.parseInt(itemTokens[3]);

        // Create new Item object
        Item itemFromFile = new Item(itemId, itemName, itemCost, itemNumInventory);

        // We have now created an Item! Return it!
        return itemFromFile;
    }

    public String marshallItem(Item item) {
        return item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventoryItems() + "\n";
    }

    public void writeFile(List<Item> list) throws VendingMachineException{
        PrintWriter out;
         try {
             //implement
             out = new PrintWriter(new FileWriter(ITEM_FILE));
        }
        catch(IOException e)
        {
            throw new VendingMachineException("Could not save item data", e);
        }

        // Write out the Item objects to the Item file.
        String itemAsText;
        for (Item currentItem : list) {
            // turn an Item into a String
            itemAsText = marshallItem(currentItem);
            // write the Item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }


    public Map<String,Item> readFile(String file) throws VendingMachineException{
        Scanner scanner;
        try {
             //implement}
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
//            return itemMap;
        }
        catch(FileNotFoundException e)
        {
            throw new VendingMachineException("File not found", e);
        }

        String currentLine;
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Student
            Item currentItem = unmarshallItem(currentLine);

            // We are going to use the student id as the map key for our student object.
            // Put currentStudent into the map using student id as the key
            itemMap.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
        return itemMap;
    }
    
}
