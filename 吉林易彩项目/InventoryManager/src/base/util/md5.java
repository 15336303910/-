/*    */ package base.util;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ public class md5
/*    */ {
/*    */   public static String strToMD5(String s)
/*    */   {
/* 21 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
/* 22 */       'A', 'B', 'C', 'D', 'E', 'F' };
/*    */     try {
/* 24 */       byte[] strTemp = s.getBytes();
/* 25 */       MessageDigest mdTemp = MessageDigest.getInstance("MD5");
/* 26 */       mdTemp.update(strTemp);
/* 27 */       byte[] md = mdTemp.digest();
/* 28 */       int j = md.length;
/* 29 */       char[] str = new char[j * 2];
/* 30 */       int k = 0;
/* 31 */       for (int i = 0; i < j; ++i) {
/* 32 */         byte byte0 = md[i];
/* 33 */         str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
/* 34 */         str[(k++)] = hexDigits[(byte0 & 0xF)];
/*    */       }
/* 36 */       return new String(str).substring(8, 24); } catch (Exception e) {
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.md5
 * JD-Core Version:    0.5.3
 */