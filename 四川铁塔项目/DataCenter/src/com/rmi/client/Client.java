package com.rmi.client;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import com.rmi.inters.IService;
public class Client{
	public static void main(String[] args){
		try{ 
			System.setProperty("sun.rmi.transport.tcp.responseTimeout","10800000"); 
			System.setProperty("sun.rmi.transport.tcp.readTimeout","10800000"); 
			System.setProperty("sun.rmi.transport.connectionTimeout","10800000"); 
			System.setProperty("sun.rmi.transport.proxy.connectTimeout","10800000"); 
			System.setProperty("sun.rmi.transport.tcp.handshakeTimeout","10800000");
			Registry registry = LocateRegistry.getRegistry("10.110.2.86",12312,new RMIClientSocketFactory() { 
				@Override
				public Socket createSocket(String host, int port)throws IOException{
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(host,port),10800000);
					return socket;
				}
			});
			IService iServiceImpl = (IService)registry.lookup("iServiceImpl");		
			Boolean doResult = iServiceImpl.doService("1");		
			System.out.println(doResult); 
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
}
