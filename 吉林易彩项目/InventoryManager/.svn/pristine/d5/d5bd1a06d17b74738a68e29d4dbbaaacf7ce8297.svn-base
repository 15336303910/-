/*    */ package base.session;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class SessionContext
/*    */ {
/*    */   private static SessionContext instance;
/*    */   private HashMap<String, HttpSession> contextMap;
/*    */ 
/*    */   private SessionContext()
/*    */   {
/* 12 */     this.contextMap = new HashMap();
/*    */   }
/*    */ 
/*    */   public static SessionContext getInstance() {
/* 16 */     if (instance == null) {
/* 17 */       instance = new SessionContext();
/*    */     }
/* 19 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void AddSession(HttpSession session) {
/* 23 */     if (session != null)
/* 24 */       this.contextMap.put(session.getId(), session);
/*    */   }
/*    */ 
/*    */   public synchronized void DelSession(HttpSession session)
/*    */   {
/* 29 */     if (session != null)
/* 30 */       this.contextMap.remove(session.getId());
/*    */   }
/*    */ 
/*    */   public synchronized HttpSession getSession(String session_id)
/*    */   {
/* 35 */     if (session_id == null)
/* 36 */       return null;
/* 37 */     return ((HttpSession)this.contextMap.get(session_id));
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.session.SessionContext
 * JD-Core Version:    0.5.3
 */