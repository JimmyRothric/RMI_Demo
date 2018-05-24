package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import modle.Account;

public interface BankService extends Remote {
	public boolean login(String name, String password) throws RemoteException;
	public boolean register(String name, String password) throws RemoteException;
	public int checkBalance() throws RemoteException;
	public String depositMoney(Integer sum) throws RemoteException;
	public String withdrawMoney(Integer sum) throws RemoteException;
	public String transferMoney(String target_name, Integer sum) throws RemoteException;
	public boolean isLogin() throws RemoteException;
	public void logout() throws RemoteException;
}
