
package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.service.*;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImplTest {
    String testFile = "DaoImplTest.txt";
    public static VendingMachineService service;
    
    public VendingMachineServiceImplTest() {
//        VendingMachineDao dao = new VendingMachineDaoImpl(testFile);
//        AuditDao auditDao = new AuditDaoImpl();
//        service = new VendingMachineServiceImpl(dao, auditDao);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceImpl.class);
    }
    
    @BeforeAll
    public static void setUpClass() throws VendingMachineException{
        //implement
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws VendingMachineException {
       //implement
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoadItemsInStock() throws VendingMachineException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        try {
            System.out.println("loadItemsInStock");
            BigDecimal bd = new BigDecimal("2.50");
            Item i1 = new Item("001", "Coke", bd, 5);

            Map<String, Item> expResult = new HashMap<>();
            expResult.put("001", i1);

            bd = new BigDecimal("2.00");
            Item i2 = new Item("002", "Sprite", bd, 3);
            expResult.put("002", i2);

            Map<String, Item> result = service.loadItemsInStock();

            assertEquals(expResult, result, "Test products loaded from the file.");
        } catch (VendingMachineNoItemInventoryException | VendingMachinePersistenceException ex) {
            fail("Product was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testGetChosenItem() throws VendingMachineNoItemInventoryException, VendingMachineException, VendingMachinePersistenceException {
        System.out.println("getChosenItem");
        BigDecimal bd = new BigDecimal("2.50");
        Item expResult = new Item("001", "Coke", bd, 5);

        Map<String, Item> Items = service.loadItemsInStock();
        Item result = service.getChosenItem("001");

        assertEquals(expResult, result, "Check both items are equal.");
        assertEquals(expResult.getName(), result.getName(), "Check if both names are equals.");
    }

    @Test
    public void testCheckSufficientMoneyToBuyProduct_VendingMachineInsufficientFundsException()
            throws VendingMachineNoItemInventoryException, VendingMachineException, VendingMachinePersistenceException, VendingMachineInsufficientFundsException {
        System.out.println("checkSufficientMoneyToBuyProduct");
        BigDecimal inputAmount = new BigDecimal("0.50");

        Map<String, Item> Items = service.loadItemsInStock();
        Item item = service.getChosenItem("001");
        Exception exception = assertThrows(VendingMachineInsufficientFundsException.class, () -> service.checkSufficientMoneyToBuyProduct(inputAmount, item));
    }

    @Test
    public void testCheckSufficientMoneyToBuyProduct()
            throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        try {
            System.out.println("checkSufficientMoneyToBuyProduct");
            BigDecimal inputAmount = new BigDecimal("3.50");

            Map<String, Item> Items = service.loadItemsInStock();
            Item item = service.getChosenItem("001");
            service.checkSufficientMoneyToBuyProduct(inputAmount, item);
        } catch (VendingMachineInsufficientFundsException | VendingMachinePersistenceException ex) {
            fail("Sufficient funds to buy product. No exception should have been thrown.");
        } catch (VendingMachineException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCalculateChange() throws VendingMachineNoItemInventoryException, VendingMachineException, VendingMachinePersistenceException {
        System.out.println("calculateChange");
        BigDecimal amount = new BigDecimal("5");

        Map<String, Item> Items = service.loadItemsInStock();
        Item item = service.getChosenItem("001");
        Change result = service.calculateChange(amount, item);

        assertEquals(10, result.getQuarters(), "Check number of quarters.");
        assertEquals(0, result.getDimes(), "Check number of dimes.");
        assertEquals(0, result.getNickels(), "Check number of nickels.");
        assertEquals(0, result.getPennies(), "Check number of pennies.");
    }

    @Test
    public void testUpdateItemSale() throws Exception {
        System.out.println("updateItemSale");

        Map<String, Item> Items = service.loadItemsInStock();
        Item item = service.getChosenItem("001");
        assertEquals(5, item.getNumInventoryItems(), "Check items in stock.");
        service.updateItemSale(item);
        Item updatedItem = service.getChosenItem("001");
        assertEquals(4, updatedItem.getNumInventoryItems(), "Check items in stock.");
    }
}
