package base.util;

import java.io.UnsupportedEncodingException;  

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
   
  
public class Base64 {  
    // 加密  
    public static String getBase64(String str) { 
    	BASE64Encoder encoder = new BASE64Encoder(); 
    	String encoded = "";
    	try{
    		encoded = encoder.encode(str.getBytes("UTF-8"));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	/*
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s=""; 
        } */ 
        return encoded;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
           BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
        }  
        return result;  
    }  
}  