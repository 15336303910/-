package com.rmi.service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import com.function.rules.service.TaskBuilder;
import com.quartz.util.ApplicationUtil;
import com.rmi.inters.IService;
public class IServiceImpl extends UnicastRemoteObject implements IService{

	/*
	 * 	远程对象必须继承java.rmi.server.UniCastRemoteObject类，这样才能保证客户端访问获得远程对象时，该
	 * 	
	 * 	远程对象将会把[自身的一个拷贝]以[Socket]的形式传输给客户端，此时客户端所获得的这个拷贝称为“存根”，而
	 * 
	 * 	服务器端本身已存在的远程对象则称之为“骨架”。
	 * 
	 * 	其实此时的存根是客户端的一个代理，用于与服务器端的通信，而骨架也可认为是服务器端的一个代理，用于接收客户
	 * 
	 * 	端的请求之后调用远程方法来响应客户端的请求。
	 * 
	 * */
	
	private static final long serialVersionUID = 4184031237245312868L;

	private String name;
	
	public IServiceImpl(String name) throws RemoteException{ 
		this.name = name;
	}
	
	@SuppressWarnings("finally")
	@Override
	public Boolean doService(String params)throws RemoteException{
		Boolean isFinished = true;
		try{
			if(params!=null && !"".equals(params)){
				String[] ids = params.split(",");
				if(ids.length>0){
					Object beanObject = ApplicationUtil.getBean("taskBuilder");
					if(beanObject!=null){
						TaskBuilder taskBuilder = (TaskBuilder)beanObject;
						System.out.println("XuHui: ======================ETL task begin.=========================.");
						for(int i=0;i<ids.length;i++){
							try{
								if(ids[i]!=null && !"".equals(ids[i])){
									System.out.println("ETL: Audit task coded by '"+ids[i]+"' work begin. ");
									taskBuilder.buildCheck(null,Integer.parseInt(ids[i]),getToken());
								}
							}catch(Exception e){
								e.printStackTrace();
								isFinished = false;
							}
						}
						System.out.println("XuHui: ==============================================================.");
					}
				}
			}
		}catch(Exception e){
			isFinished = false;
			e.printStackTrace();
		}finally{
			return isFinished;
		}
	}
	
	public String getToken(){
		String jobToken = "";
		List<String> pool = new ArrayList<String>();
		pool.add("A");
		pool.add("B");
		pool.add("C");
		pool.add("D");
		pool.add("E");
		pool.add("F");
		pool.add("G");
		pool.add("H");
		pool.add("I");
		pool.add("J");
		pool.add("K");
		pool.add("L");
		pool.add("M");
		pool.add("N");
		pool.add("O");
		pool.add("P");
		pool.add("Q");
		pool.add("R");
		pool.add("S");
		pool.add("T");
		pool.add("U");
		pool.add("V");
		pool.add("W");
		pool.add("X");
		pool.add("Y");
		pool.add("Z");
		while(jobToken.length()<=30){
			Double randomDouble = Math.random()*25;
			Integer randomIndex = randomDouble.intValue();
			if(randomIndex>25){
				randomIndex = 25;
			}
			jobToken+=pool.get(randomIndex);
		}
		return jobToken;
	}
}
