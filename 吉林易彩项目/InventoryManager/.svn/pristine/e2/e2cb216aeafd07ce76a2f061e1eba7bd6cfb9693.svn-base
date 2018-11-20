/*    */ package base.session;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ 
/*    */ public class SessionListener
/*    */   implements HttpSessionListener
/*    */ {
/* 10 */   public static Map<String, HttpSession> userMap = new HashMap();
/* 11 */   private SessionContext sc = SessionContext.getInstance();
/*    */ 
/*    */   public void sessionCreated(HttpSessionEvent httpSessionEvent) {
/* 14 */     this.sc.AddSession(httpSessionEvent.getSession());
/*    */   }
/*    */ 
/*    */   public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
/* 18 */     HttpSession session = httpSessionEvent.getSession();
/* 19 */     this.sc.DelSession(session);
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.session.SessionListener
 * JD-Core Version:    0.5.3
 */