/*     */ package interfaces.pdainterface.equt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import manage.equt.pojo.EqutInfoBean;
/*     */ import manage.equt.service.EqutInfoService;
/*     */ import manage.user.pojo.UserInfoBean;
/*     */ import org.springframework.web.context.WebApplicationContext;
/*     */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*     */ 
/*     */ public class GetPos extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1784487641312470805L;
/*     */   private EqutInfoService equtInfoService;
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  40 */     super.destroy();
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  56 */     execGetPos(request, response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  733 */     execGetPos(request, response);
/*     */   }
/*     */ 
/*     */   public void execGetPos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  78 */     String eid = request.getParameter("eid");
/*  79 */     EqutInfoBean equtInfoBean = new EqutInfoBean();
/*  80 */     UserInfoBean userBean = (UserInfoBean)request.getSession().getAttribute("userBean");
/*     */ 
/*  83 */     String lon = "0";
/*  84 */     String lat = "0";
/*  85 */     String addr = "";
/*     */     try
/*     */     {
/*  89 */       equtInfoBean.setEid(eid);
/*  90 */       equtInfoBean.setAreano(userBean.getAreano());
/*  91 */       equtInfoBean = this.equtInfoService.getEqutInfoBean(equtInfoBean);
/*  92 */       lon = equtInfoBean.getLon();
/*  93 */       lat = equtInfoBean.getLat();
/*  94 */       addr = URLEncoder.encode(equtInfoBean.getEaddr(), "utf-8");
/*     */     }
/*     */     catch (Exception e) {
/*  97 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 101 */     response.setContentType("text/plain");
/* 102 */     response.setHeader("Charset", "UTF-8");
/* 103 */     PrintWriter out = response.getWriter();
/* 104 */     out.println("{\"lon\":\"" + lon + "\",\"lat\":\"" + lat + "\",\"addr\":\"" + addr + "\"}");
/* 105 */     out.flush();
/* 106 */     out.close();
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config)
/*     */     throws ServletException
/*     */   {
/* 114 */     super.init(config);
/* 115 */     ServletContext servletContext = getServletContext();
/* 116 */     WebApplicationContext wac = null;
/* 117 */     wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
/* 118 */     setEqutInfoService((EqutInfoService)wac.getBean("equtInfoService"));
/*     */   }
/*     */ 
/*     */   public void setEqutInfoService(EqutInfoService equtInfoService) {
/* 122 */     this.equtInfoService = equtInfoService;
/*     */   }
/*     */ 
/*     */   public EqutInfoService getEqutInfoService() {
/* 126 */     return this.equtInfoService;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.pdainterface.equt.GetPos
 * JD-Core Version:    0.5.3
 */