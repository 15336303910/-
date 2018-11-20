/*    */ package base.web;
/*    */ 
/*    */ import com.opensymphony.xwork2.ActionContext;
/*    */ import com.opensymphony.xwork2.ActionSupport;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts2.ServletActionContext;
/*    */ 
/*    */ public class BaseAction extends ActionSupport
/*    */ {
/*    */   private static final long serialVersionUID = 2092167880122968020L;
/*    */ 
/*    */   public HttpSession getSession()
/*    */   {
/* 26 */     HttpSession session = getRequest().getSession();
/* 27 */     return session;
/*    */   }
/*    */ 
/*    */   public HttpServletRequest getRequest()
/*    */   {
/* 36 */     HttpServletRequest request = ServletActionContext.getRequest();
/* 37 */     return request;
/*    */   }
/*    */ 
/*    */   public ServletContext getServletContext()
/*    */   {
/* 46 */     return ((ServletContext)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.ServletContext"));
/*    */   }
/*    */ 
/*    */   public HttpServletResponse getResponse() {
/* 50 */     return ServletActionContext.getResponse();
/*    */   }
/*    */ }
