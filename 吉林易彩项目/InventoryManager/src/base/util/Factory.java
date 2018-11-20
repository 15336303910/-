/*    */ package base.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class Factory
/*    */ {
/* 10 */   private static Properties props = new Properties();
/*    */ 
/*    */   static {
/* 13 */     ClassLoader loader = 
/* 14 */       Factory.class.getClassLoader();
/*    */ 
/* 16 */     InputStream ips = loader
/* 17 */       .getResourceAsStream("webClient.properties");
/*    */     try {
/* 19 */       props.load(ips);
/*    */     } catch (IOException e) {
/* 21 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getValue(String key) {
/* 26 */     return props.getProperty(key);
/*    */   }
/*    */ 
/*    */   public static Object getInstance(String className) {
/* 30 */     Object obj = null;
/*    */     try
/*    */     {
/* 37 */       obj = Class.forName(className).newInstance();
/*    */     } catch (Exception e) {
/* 39 */       e.printStackTrace();
/*    */     }
/* 41 */     return obj;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.Factory
 * JD-Core Version:    0.5.3
 */