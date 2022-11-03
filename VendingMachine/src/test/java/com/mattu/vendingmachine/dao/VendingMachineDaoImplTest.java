
package com.mattu.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salajrawi
 */
public class VendingMachineDaoImplTest {
    String testFile = "DaoImplTest.txt";
    VendingMachineDao testDao = new VendingMachineDaoImpl(testFile);

    public VendingMachineDaoImplTest() {
        ;
    }

//    @org.junit.jupiter.api.BeforeAll
//    public static void setUpClass() throws Exception {
//        VendingMachineDao testDao = new VendingMachineDaoImpl(testFile);
//    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

//    @org.junit.jupiter.api.BeforeEach
//    public void setUp() throws Exception {
//        testDao = new VendingMachineDaoImpl();
//    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    @Test
    public void testLoadItemsFromFile() throws Exception {
        System.out.println("Load Items from File");
        BigDecimal bd = new BigDecimal("2.50");
        Item i1 = new Item("1", "Coke", bd, 5);
        bd = new BigDecimal("2.00");
        Item i2 = new Item("2", "Sprite", bd, 3);
        Map<String, Item> expResult = new HashMap<>();
        expResult.put("1", i1);
        expResult.put("2", i2);

        Map<String, Item> result = testDao.loadItemsFromFile();

        assertEquals(expResult, result, "Test Items loaded from file");
    }

    @Test
    public void testAddGetItem() {
        System.out.println("addItem");
        BigDecimal bd = new BigDecimal("2.50");
        Item i1 = new Item("001", "Water", bd, 10);
        testDao.addItem(i1.getItemId(), i1);

        Item result = testDao.getItem(i1.getItemId());

        Item expResult = new Item("001", "Water", bd, 10);

        assertEquals(expResult.getItemId(), result.getItemId(), "Checking item id matches.");
        assertEquals(expResult.getName(), result.getName(), "Checking item name matches.");
        assertEquals(expResult.getCost(), result.getCost(), "Checking item cost matches.");
        assertEquals(expResult.getNumInventoryItems(), result.getNumInventoryItems(), "Checking inventory count matches.");
    }

    @Test
    public void testGetAllItems() {
        System.out.println("getAllItems");
        BigDecimal bd = new BigDecimal("2.50");
        Item i1 = new Item("001", "Coke", bd, 5);
        bd = new BigDecimal("2.00");
        Item i2 = new Item("002", "Sprite", bd, 3);
        testDao.addItem(i1.getItemId(), i1);
        testDao.addItem(i2.getItemId(), i2);

        List<Item> result = testDao.getAllItems();
        List<Item> expResult = new ArrayList<>();

        expResult.add(i1);
        expResult.add(i2);

        assertNotNull(result, "The list of items must not be null");
        assertEquals(2, result.size(), "The list of products should have 2 products");
        assertTrue(result.contains(i1), "The list of items should contain a Coke");
        assertTrue(result.contains(i2), "The list of items should contain a Sprite");
        assertEquals(expResult, result, "The 2 lists of items should be the same.");
    }

    @Test
    public void testRemoveItem() {
        System.out.println("removeItems");
        BigDecimal bd = new BigDecimal("2.50");
        Item i1 = new Item("001", "Coke", bd, 5);
        bd = new BigDecimal("2.00");
        Item i2 = new Item("002", "Sprite", bd, 3);
        testDao.addItem(i1.getItemId(), i1);
        testDao.addItem(i2.getItemId(), i2);

        Item removedItem = testDao.removeItem(i1.getItemId());
        assertEquals(removedItem, i1, "The removed item should be Coke.");
        List<Item> result = testDao.getAllItems();
        assertNotNull(result, "The list of items must not be null.");
        assertEquals(1, result.size(), "The list of items should have 1 item.");

        removedItem = testDao.removeItem(i2.getItemId());
        assertEquals(removedItem, i2, "The removed item should be Sprite.");

        result = testDao.getAllItems();
        assertEquals(0, result.size(), "The list of items should be empty.");

        Item retrievedItem = testDao.getItem(i1.getItemId());
        assertNull(retrievedItem, "Coke was removed, should be null.");
        retrievedItem = testDao.getItem(i2.getItemId());
        assertNull(retrievedItem, "Sprite was removed, should be null.");

    }

    @Test
    public void testUpdateItem() {
        System.out.println("updateItems");
        BigDecimal bd = new BigDecimal("2.50");
        Item i1 = new Item("001", "Coke", bd, 5);
        testDao.addItem(i1.getItemId(), i1);

        bd = new BigDecimal("1.00");
        i1.setName("Water");
        i1.setCost(bd);
        i1.setNumInventoryItems(12);
        testDao.updateItem(i1.getItemId(), i1);

        Item result = testDao.updateItem(i1.getItemId(), i1);
        Item expResult = new Item("001", "Water", bd, 12);

        assertEquals(expResult, result, "Updated item is Water");
        assertEquals("001", result.getItemId(), "Updated item ID is 001");
        assertEquals(bd, result.getCost(), "Updated item cost is 1.00");
        assertEquals(12, result.getNumInventoryItems(), "Updated items in stock is 12");
    }
}
