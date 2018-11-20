package interfaces.irmsInterface.utils;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestMain {

	public static void main(String[] args) {
		try{
			String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><check loaded=\"错误\"><errors><error mc=\"管道段\" type=\"error\" id=\"1510848164\" name=\"北京市西城区珠市口西大街29号北侧租用中国联通管道_北2号井-北京市西城区珠市口西大街29号北侧租用中国联通管道_北3号井\" info=\"两个承载点之间只能建一条承载段\"/></errors></check>" ;
			
			String newId = XmlUtil.getErrorId(xml);
			System.out.println(newId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
