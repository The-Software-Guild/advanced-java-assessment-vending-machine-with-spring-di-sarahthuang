
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author salajrawi
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException {
//        UserIO myIo = new UserIOImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingMachineDao myDao = new VendingMachineDaoImpl();
//        AuditDao myAuditDao = new AuditDaoImpl();
//        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);
//        VendingMachineController controller = new VendingMachineController(myView, myService);
//        controller.run();

        //Replacing code above with spring container
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
