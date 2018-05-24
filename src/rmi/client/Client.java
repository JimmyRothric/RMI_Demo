package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

import rmi.service.BankService;

public class Client {
	
	public static void ServiceStart(BankService bankService) throws RemoteException {
		boolean isLogin = bankService.isLogin();
	
		while (!isLogin) {
			int option = 0;
			System.out.println("1.登录\t2.注册");
			Scanner in = new Scanner(System.in);
			option = in.nextInt();
			String name = null;
			String password = null;
			boolean success = false;
			switch (option) {
			case 1:
				//登录
				System.out.println("请输入用户名");
				name = in.next();
				System.out.println("请输入密码");
				password = in.next();
				success = bankService.login(name, password);
				if (success) {
					isLogin = bankService.isLogin();
					System.out.println("登录成功");
				} else {
					System.err.print("登录失败:用户名或密码错误\n");
				}
				break;
			case 2:
				//注册
				System.out.println("请输入用户名");
				name = in.next();
				System.out.println("请输入密码");
				password = in.next();
				success = bankService.register(name, password);
				if (success) {
					isLogin = bankService.isLogin();
					System.out.println("注册成功");
				} else {
					System.err.print("注册失败:该账号已存在或出现其他异常\n");
				}
				break;
			default:
				break;
			}
		}
		
		boolean finished = false;
		while (isLogin && !finished) {
			try{
				System.out.println("选择服务:\n1.存款\t2.取款\n3.转账\t4.查询余额\n5.退出");
				int choice = 0;
				int sum;
				String target_name;
				String result = null;
				
				Scanner in = new Scanner(System.in);
				choice = in.nextInt();
				switch (choice) {
					case 1:
						//存款
						System.out.println("输入存款金额");
						sum = in.nextInt();
						result = bankService.depositMoney(sum);
						System.out.println(result);
						break;
					case 2:
						//取款
						System.out.println("输入取款金额");
						sum = in.nextInt();
						result = bankService.withdrawMoney(sum);
						System.out.println(result);
						break;
					case 3:
						//转账
						System.out.println("输入转账目标账户");
						target_name = in.next();
						System.out.println("输入转账金额");
						sum = in.nextInt();
						result = bankService.transferMoney(target_name, sum);
						System.out.println(result);
						break;
					case 4:
						//查询余额
						int balance = bankService.checkBalance();
						System.out.println("当前余额:$" + balance);
						break;
					case 5:
						bankService.logout();
						finished = true;
						break;
					default:
						System.err.print("输入格式错误\n");
						break;
				}
			} catch (InputMismatchException e) {
				System.err.print("请输入数字\n");
			} 
		}
		System.out.println("SYSTEM IS CLOSED");
		
	}

	public static void main(String args[]) throws Exception {
		
		try {
			BankService bankService = (BankService) Naming.lookup("rmi://localhost:1099/BankService");
			ServiceStart(bankService);
		} catch (MalformedURLException e) {
			System.out.println("url格式异常");
		} catch (RemoteException e) {
			System.out.println("创建对象异常");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("对象未绑定");
		}
		
	}
	
	
}
