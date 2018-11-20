package com.rmi.inters;
import java.rmi.Remote;
import java.rmi.RemoteException;
/*
 * 	RMI：Remote Method Invoke，远程方法调用
 * 
 *  在Java中，只要一个类extends了Remote接口，即可成为存在于服务器端的远程对象，供客户端访问并提供一定的服务。
 *  
 *  JavaDoc描述：Remote接口用于标识其方法可以从非本地虚拟机上调用的接口。任何远程对象都必须直接或间接实现此接口。
 * 
 * */
public interface IService extends Remote{
	/*
	 * 	注意：extends了Remote接口的类,其接口中的方法若是声明抛出了RemoteException异常，则表明该方法可被客户端远程访问调用。
	 * 
	 * */
	public Boolean doService(String params)throws RemoteException;
}
