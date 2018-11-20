package base.util;

import javax.xml.namespace.QName;

import manage.user.web.maintainGroupAction;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class SmsUtil {
	
	
	/**
	 * 106582480912345
	 * 派发短信
	 * @param tel
	 * @param contents
	 * @return
	 */
	public static  String sendSms(String tel,String contents){
		String result ="";
		try{
			
			/*String encode = getEncoding(contents);
			StringBuffer msgStringBuffer = new StringBuffer();
			// 短信
			msgStringBuffer.append("<sms username='").append("rms").append("' password='" ).append("Rms,528").append("'>");
			msgStringBuffer.append("<head system='system' service='service'  priority='2' seqno='12345'/>");
			msgStringBuffer.append("<mobile>").append(tel).append("</mobile>");
			msgStringBuffer.append("<message>").append(contents).append("</message>");
			msgStringBuffer.append("</sms>");
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress("http://10.224.145.16:7500/smsIRMS/smsSend_proxy?wsdl");
			call.setOperationName(new QName("http://10.224.145.16:7500/smsIRMS/smsSend_proxy?wsdl", "sendSMS"));
			result = String.valueOf(call.invoke(new Object[] {msgStringBuffer.toString()}));*/
			result = "派发成功";
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
		String encode = "GB2312";      
		try {      
			if (str.equals(new String(str.getBytes(encode), encode))) {      
				String s = encode;      
				return s;      
			}      
		} catch (Exception exception) {      
		}      
		encode = "ISO-8859-1";      
		try {      
			if (str.equals(new String(str.getBytes(encode), encode))) {      
				String s1 = encode;      
				return s1;      
			}      
		} catch (Exception exception1) {      
		}      
		encode = "UTF-8";      
		try {      
			if (str.equals(new String(str.getBytes(encode), encode))) {      
				String s2 = encode;      
				return s2;      
			}      
		} catch (Exception exception2) {      
		}      
		encode = "GBK";      
		try {      
			if (str.equals(new String(str.getBytes(encode), encode))) {      
				String s3 = encode;      
				return s3;      
			}      
		} catch (Exception exception3) {      
		}      
		return "";      
	}      
	
	
	public static void main(String[] args) {
		String result = SmsUtil.sendSms("15001369497", "赵柱同学请给到楼底下拿你的盒饭!");
		System.out.println(result);
	}

}
