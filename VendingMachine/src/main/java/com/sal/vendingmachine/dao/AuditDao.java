
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;

/**
 *
 * @author salajrawi
 */
public interface AuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
