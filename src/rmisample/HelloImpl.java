package rmisample;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	private static final long serialVersionUID = -271947229644133464L;

	protected HelloImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String sayHello(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return "Hello,"+name;
	}

}
