package interfaces.irmsInterface.interfaces.extnode;

import java.rmi.RemoteException;

public class TextMain {
	
	public static void main(String[] args) {
		try{
			/*ExtNodeServiceSoapBindingStub sub =new ExtNodeServiceSoapBindingStub();
			sub.inceptOrder("ceshi diaoyong ");*/
			String inXml ="<?xml version=\"1.0\" encoding=\"GB2312\"?>"
					+ "<esb>"
					+ "";
			ExtNodeServiceServiceLocator extnode = new ExtNodeServiceServiceLocator();
			ExtNodeServiceSoapBindingStub stub = (ExtNodeServiceSoapBindingStub) extnode.getextNodeService();
			System.out.println(stub.inceptOrder(inXml));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
