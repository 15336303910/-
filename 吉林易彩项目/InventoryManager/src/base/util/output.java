/*    */ package base.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class output
/*    */ {
/*    */   public static void ouptXml(String xmlStr, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/* 12 */     response.setContentType("text/html");
/* 13 */     response.setCharacterEncoding("utf-8");
/* 14 */     PrintWriter out = response.getWriter();
/* 15 */     out.println(xmlStr);
/* 16 */     out.flush();
/* 17 */     out.close();
/*    */   }
/*    */ 
/*    */   public static void outputXml(String xmlStr, HttpServletResponse response) throws IOException
/*    */   {
/* 22 */     response.setContentType("text/xml");
/* 23 */     response.setCharacterEncoding("utf-8");
/* 24 */     PrintWriter out = response.getWriter();
/* 25 */     out.println(xmlStr);
/* 26 */     out.flush();
/* 27 */     out.close();
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.output
 * JD-Core Version:    0.5.3
 */