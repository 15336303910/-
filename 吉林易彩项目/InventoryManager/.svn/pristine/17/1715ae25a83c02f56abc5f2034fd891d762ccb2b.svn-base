/*    */ package base.util;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DateForStrUtil
/*    */ {
/*    */   public static String getTimeStr(Date date, int overday)
/*    */   {
/* 19 */     long theDay = 86400000L;
/* 20 */     long theHour = 3600000L;
/* 21 */     long theMinutes = 60000L;
/*    */ 
/* 23 */     long nowlong = System.currentTimeMillis();
/* 24 */     long datelong = date.getTime();
/*    */ 
/* 26 */     long time = nowlong - (datelong + theHour * overday);
/* 27 */     if (time < 0L)
/*    */     {
/* 29 */       time *= -1L;
/*    */     }
/*    */ 
/* 33 */     long nowDay = 0L; long nowHour = 0L; long nowMinutes = 0L; long nowSenconds = 0L;
/* 34 */     long nowTime = 0L;
/* 35 */     StringBuffer sb = new StringBuffer();
/*    */ 
/* 37 */     if (time - theDay >= 0L)
/*    */     {
/* 39 */       nowDay = time / theDay;
/* 40 */       nowTime = time % theDay;
/*    */ 
/* 42 */       if (nowTime - theHour >= 0L)
/* 43 */         nowHour = nowTime / theHour;
/*    */       else {
/* 45 */         theHour = 0L;
/*    */       }
/* 47 */       sb.append(nowDay + "天");
/* 48 */       sb.append(nowHour + "小时");
/*    */     }
/* 51 */     else if (time - theHour >= 0L) {
/* 52 */       nowHour = time / theHour;
/* 53 */       nowTime = time % theHour;
/* 54 */       if (nowTime - theMinutes >= 0L)
/* 55 */         nowMinutes = nowTime / theMinutes;
/*    */       else
/* 57 */         nowMinutes = 0L;
/* 58 */       sb.append(nowHour + "小时");
/* 59 */       sb.append(nowMinutes + "分钟");
/*    */     }
/* 62 */     else if ((time - theHour < 0L) && (time - theMinutes >= 0L)) {
/* 63 */       nowMinutes = time / theMinutes;
/* 64 */       sb.append("0小时" + nowMinutes + "分");
/*    */     }
/*    */     else {
/* 67 */       nowSenconds = time;
/*    */ 
/* 69 */       sb.append("0分" + nowSenconds + "秒");
/*    */     }
/* 71 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.DateForStrUtil
 * JD-Core Version:    0.5.3
 */