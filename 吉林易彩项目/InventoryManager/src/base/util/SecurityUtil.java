/*    */ package base.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.net.SocketException;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public class SecurityUtil
/*    */ {
/* 16 */   private static final byte[] KEYVALUE = "#qdincrease#".getBytes();
/*    */   private static final int BUFFERLEN = 512;
/*    */ 
/*    */   public static boolean decrypt(String root)
/*    */   {
/*    */     try
/*    */     {
/* 21 */       String licensePath = root + "WEB-INF" + File.separator + "classes" + File.separator + "licenses";
/*    */ 
/* 23 */       FileInputStream in = new FileInputStream(licensePath);
/* 24 */       String str = "";
/*    */ 
/* 26 */       int pos = 0;
/* 27 */       int keylen = KEYVALUE.length;
/* 28 */       byte[] buffer = new byte[512];
/*    */       int c;
/* 29 */       while ((c = in.read(buffer)) != -1) {
/* 30 */         for (int i = 0; i < c; ++i)
/*    */         {
/*    */           int tmp83_81 = i;
/*    */           byte[] tmp83_79 = buffer; tmp83_79[tmp83_81] = (byte)(tmp83_79[tmp83_81] ^ KEYVALUE[pos]);
/* 32 */           ++pos;
/* 33 */           if (pos == keylen)
/* 34 */             pos = 0;
/*    */         }
/*    */       }
/* 37 */       in.close();
/* 38 */       str = new String(buffer);
/* 39 */       String[] sArray = str.split("#");
/* 40 */       sArray[2] = sArray[2].substring(0, 10);
/*    */ 
/* 42 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 43 */       Date beginTime = sdf.parse(sArray[1]);
/* 44 */       Date endTime = sdf.parse(sArray[2]);
/* 45 */       Date now = new Date();
/* 46 */       String localAddress = getHostAddress();
/*    */ 
/* 50 */       return ((localAddress != null) && (localAddress.equals(sArray[0])) && 
/* 49 */         (endTime.after(now)) && (now.after(beginTime)));
/*    */     }
/*    */     catch (FileNotFoundException e)
/*    */     {
/* 55 */       return false;
/*    */     } catch (IOException e) {
/* 57 */       return false; } catch (ParseException e) {
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   public static String getHostAddress()
/*    */   {
/*    */     try
/*    */     {
/* 66 */       NetworkInterface ni = NetworkInterface.getByName("eth0");
/* 67 */       Enumeration ips = ni.getInetAddresses();
/* 68 */       while (ips.hasMoreElements()) {
/* 69 */         String ip = ((InetAddress)ips.nextElement()).getHostAddress();
/* 70 */         if (ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")) {
/* 71 */           return ip;
/*    */         }
/*    */       }
/* 74 */       return null; } catch (SocketException e) {
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.SecurityUtil
 * JD-Core Version:    0.5.3
 */