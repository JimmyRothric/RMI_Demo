package rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.AccountDao;
import modle.Account;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private boolean isLogin;
	public BankServiceImpl() throws RemoteException {
		account = null;
		isLogin = false;
	}
	
	@Override
	public boolean login(String name, String password) throws RemoteException {

		AccountDao dao = new AccountDao();
		try {
			account = dao.isValid(name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (account == null) {
			return false;
		} else {
			isLogin = true;
			return true;
		}
	}
	
	@Override
	public boolean register(String name, String password) throws RemoteException {
		
		AccountDao dao = new AccountDao();
		
		boolean isExisted = false;
		boolean success = false;
		try {
			isExisted = dao.isExistedName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isExisted) {
			return false;
		} else {
			account = new Account(name, password, 0);
			try {
				success = dao.addAccount(account);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (success) {
				isLogin = true;
			}
		}
		return success;
	}
	
	//获取账户
	public Account getAccoutByName(String name) {
		
		AccountDao dao = new AccountDao();
		Account acc = null;
		try {
			acc = dao.findAccountByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acc;
	}
	
	@Override
	public boolean isLogin() throws RemoteException{
		return isLogin;
	}
	
	@Override
	//查询余额
	public int checkBalance() throws RemoteException {
		
		AccountDao dao = new AccountDao();
		int balance = 0;
		try {
			balance = dao.checkBalance(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	@Override
	//存款
	public String depositMoney(Integer sum) throws RemoteException {
		
		AccountDao dao = new AccountDao();
		boolean success = false;
		
		if (sum < 0) {
			return ("存款失败:输入正整数金额\n");
		}
		try {
			success = dao.AccessMoney(account, sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success) {
			return ("存款成功\t当前余额:$" + checkBalance());
		} else {
			return ("存款失败:DEPOSIT MONEY ERROR\n");
		}
	}
	
	@Override
	//取款
	public String withdrawMoney(Integer sum) throws RemoteException {
		
		AccountDao dao = new AccountDao();
		boolean success = false;
		
		if (sum < 0){
			return ("取款失败:输入正整数金额\n");
		}
		if (checkBalance() < sum) {
			return ("取款失败:余额不足\n");
		} else {
			try {
				success = dao.AccessMoney(account, -sum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (success) {
			return ("取款成功\t当前余额:$" + checkBalance());
		} else {
			return ("取款失败:WITHDRAW MONEY ERROR\n");
		}
	}
	
	@Override
	//转账
	public String transferMoney(String target_name, Integer sum) throws RemoteException {
		AccountDao dao = new AccountDao();
		if (account.getName().equals(target_name)) {
			return ("转账失败:无法自转账给当前账户\n");
		}
		if (sum < 0){
			return ("转账失败:输入正整数金额\n");
		}
		try {
		
			if (checkBalance() < sum) { 
				return ("转账失败:余额不足\n");
			} else if (!dao.isExistedName(target_name)) {
				return ("转账失败:目标账户不存在\n");
			} else {
				Account target_acc = getAccoutByName(target_name);
				boolean withdraw_completed = dao.AccessMoney(account, -sum);
				if (withdraw_completed) {
					boolean disposit_completed = dao.AccessMoney(target_acc, sum);
					if (disposit_completed){
						return ("转账成功\t当前余额:$" + checkBalance());
					} else {
						dao.AccessMoney(account, sum);
						return ("转账失败:TRANSFER MONEY ERROR\n");
					}
					
				} else {
					return ("转账失败:TRANSFER MONEY ERROR\n");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("转账失败:TRANSFER MONEY ERROR\n");
		
	}

	@Override
	public void logout() throws RemoteException {
		isLogin = false;
		account = null;
	}

}
