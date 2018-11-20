/*    */ package base.filters;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ReDispatcherFilter
/*    */   implements Filter
/*    */ {
/* 45 */   private ArrayList<String> includes = new ArrayList();
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 33 */     HttpServletRequest request = (HttpServletRequest)req;
/* 34 */     String target = request.getRequestURI();
/* 35 */     target = (target.lastIndexOf("?") > 0) ? 
/* 36 */       target.substring(target.lastIndexOf("/") + 1, target.lastIndexOf("?") - target.lastIndexOf("/")) : 
/* 37 */       target.substring(target.lastIndexOf("/") + 1);
/* 38 */     if (this.includes.contains(target)) {
/* 39 */       RequestDispatcher rdsp = request.getRequestDispatcher(target);
/* 40 */       rdsp.forward(req, resp);
/*    */     } else {
/* 42 */       chain.doFilter(req, resp);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig config)
/*    */     throws ServletException
/*    */   {
/* 49 */     this.includes.addAll(Arrays.asList(config.getInitParameter(
/* 50 */       "includeServlets").split(",")));
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.filters.ReDispatcherFilter
 * JD-Core Version:    0.5.3
 */