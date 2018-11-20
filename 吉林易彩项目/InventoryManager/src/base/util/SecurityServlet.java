/*    */ package base.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import manage.user.pojo.UserInfoBean;
/*    */ 
/*    */ public class SecurityServlet extends HttpServlet
/*    */   implements Filter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
/*    */     throws IOException, ServletException
/*    */   {
/* 22 */     HttpServletRequest request = (HttpServletRequest)arg0;
/* 23 */     HttpServletResponse response = (HttpServletResponse)arg1;
/* 24 */     HttpSession session = request.getSession(true);
/* 25 */     UserInfoBean userBean = (UserInfoBean)session.getAttribute("userBean");
/* 26 */     String url = request.getRequestURI();
/* 27 */     String root = session.getServletContext().getRealPath("/");
/* 28 */     if ((userBean == null) && 
/* 30 */       (url != null) && (!(url.equals(""))) && (!(url.contains("licensesError"))) && 
/* 39 */       (url.indexOf("Login") < 0) && (url.indexOf("login") < 0) && (url.indexOf("report") < 0) && (url.indexOf("Report") < 0))
/*    */     {
/* 41 */       response.sendRedirect(request.getContextPath() + "/login.jsp");
/* 42 */       return;
/*    */     }
/*    */ 
/* 46 */     arg2.doFilter(arg0, arg1);
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig arg0)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.SecurityServlet
 * JD-Core Version:    0.5.3
 */