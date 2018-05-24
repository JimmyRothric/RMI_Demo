package rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import rmi.service.BankService;
import rmi.service.BankServiceImpl;

public class Server extends Thread {
	
	public static void main(String args[]) throws Exception {
		try { 
			BankService service = new BankServiceImpl();
			LocateRegistry.createRegistry(1099);  
			Naming.bind("rmi://localhost:1099/BankService", service);  
	        System.out.println("BankServer启动成功");  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }


}
