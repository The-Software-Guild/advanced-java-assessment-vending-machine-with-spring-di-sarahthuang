
package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author salajrawi
 */
public class Item {
    private String itemId;
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;
    private final String DELIMITER = "::";

    public Item(String itemId, String name, BigDecimal cost, int numInventoryItems) {
       //implement
        this.itemId = itemId;
        this.name = name;
        this.cost = cost;
        this.numInventoryItems = numInventoryItems;
    }

    public Item(String itemAsText) throws VendingMachinePersistenceException {
        try {
            String[] fields = itemAsText.split(DELIMITER); // PatternSyntaxException
            this.itemId = fields[0];
            this.name = fields[1];
            this.cost = new BigDecimal(fields[2]);
            this.numInventoryItems = Integer.parseInt(fields[3]); // NullPointerException | NumberFormatException
        } catch (PatternSyntaxException | NullPointerException | NumberFormatException ex) {
            throw new VendingMachinePersistenceException(ex.getMessage());
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getNumInventoryItems() {
        return numInventoryItems;
    }

    public void setNumInventoryItems(int numInventoryItems) {
        this.numInventoryItems = numInventoryItems;
    }

//    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", cost=" + cost + ", numInventoryItems=" + numInventoryItems + '}';
    }

//    @Override
    public int hashCode() {
        //implement
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.itemId);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.cost);
        hash = 79 * hash + this.numInventoryItems;
        return hash;
    }

//    @Override
    public boolean equals(Object obj) {
        //implement
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        //Type-casting obj to type Item
        final Item other = (Item)obj;

        if (this.numInventoryItems != other.numInventoryItems) {
            System.out.println("numInventoryItems returned false");
            return false;
        }
        if (!Objects.equals(this.itemId, other.itemId)) {
            System.out.println("itemId returned false");
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            System.out.println("name returned false");
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            System.out.println("cost returned false");
            return false;
        }
        return true;
    }
}
