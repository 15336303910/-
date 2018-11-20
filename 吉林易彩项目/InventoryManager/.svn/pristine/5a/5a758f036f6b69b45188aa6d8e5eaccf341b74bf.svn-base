/*    */ package interfaces.pdainterface.interfaceUtil;
/*    */ 
/*    */ import base.util.output;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class InterfaceUtil
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(InterfaceUtil.class);
/*    */ 
/*    */   public static void packageReturn(HttpServletResponse response, String serialNo, String resultStr, String resultDes, String returnResult)
/*    */   {
/* 50 */     String responseResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><WEBSERVICESYNC><HEADER></HEADER><BODY><SERIALNO>" + 
/* 53 */       serialNo + "</SERIALNO>" + 
/* 54 */       "<RESULT>" + resultStr + "</RESULT>" + 
/* 55 */       "<DESCRIPTION>" + resultDes + "</DESCRIPTION>" + 
/* 56 */       returnResult + 
/* 57 */       "</BODY></WEBSERVICESYNC>";
/*    */     try
/*    */     {
/* 61 */       output.ouptXml(responseResult, response);
/*    */     } catch (IOException e) {
/* 63 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.pdainterface.interfaceUtil.InterfaceUtil
 * JD-Core Version:    0.5.3
 */