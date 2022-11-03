
package com.sal.vendingmachine.service;

/**
 *
 * @author salajrawi
 */
public class VendingMachineNoItemInventoryException extends Exception{
    public VendingMachineNoItemInventoryException(String msg){
        super(msg);
    }
    
    public VendingMachineNoItemInventoryException(String msg, Throwable cause){
        super(msg,cause);
    }
}
