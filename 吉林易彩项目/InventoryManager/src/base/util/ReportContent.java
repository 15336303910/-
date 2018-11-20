/*     */ package base.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class ReportContent extends HttpServlet
/*     */ {
/*     */   public void destroy()
/*     */   {
/*  24 */     super.destroy();
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  41 */     exec(request, response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  56 */     exec(request, response);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void exec(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  75 */     response.setContentType("text/html");
/*  76 */     PrintWriter out = response.getWriter();
/*  77 */     String htmlstr = "";
/*  78 */     String pvalue = request.getParameter("pvalue");
/*  79 */     htmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
/*  80 */     htmlstr = htmlstr + "<chart bgColor=\"cccccc\" bgAlpha=\"100\" gaugestartAngle=\"235\" gaugeendAngle=\"-55\" lowerLimit=\"0\" upperLimit=\"100\" majorTMNumber=\"11\" majorTMThickness=\"5\" majorTMColor=\"F48900\" majorTMHeight=\"15\" minorTMNumber=\"4\" minorTMThickness=\"2\" minorTMColor=\"000000\" minorTMHeight=\"13\" placeValuesInside=\"1\" gaugeOuterRadius=\"140\" gaugeInnerRadius=\"85%25\" baseFontColor=\"000000\" baseFont=\"Impact\" baseFontSize=\"30\" showShadow=\"0\" pivotRadius=\"20\" pivotFillColor=\"000000,383836\" pivotFillType=\"linear\" pivotFillRatio=\"50,50\" pivotFillAngle=\"240\" annRenderDelay=\"0\">";
/*  81 */     htmlstr = htmlstr + "<dials>";
/*  82 */     htmlstr = htmlstr + "<dial value=\"" + pvalue + "\" color=\"E70E00\" borderColor=\"E70E00\" baseWidth=\"25\" topWidth=\"1\" radius=\"85\" />";
/*  83 */     htmlstr = htmlstr + "</dials>";
/*  84 */     htmlstr = htmlstr + "<trendpoints>";
/*  85 */     htmlstr = htmlstr + "<point startValue=\"0\" endValue=\"90\" radius=\"140\" innerRadius=\"0\" color=\"339900\" alpha=\"35\" showBorder=\"0\" />";
/*  86 */     htmlstr = htmlstr + "<point startValue=\"90\" endValue=\"100\" radius=\"140\" innerRadius=\"0\" color=\"990000\" alpha=\"35\" showBorder=\"0\" />";
/*  87 */     htmlstr = htmlstr + "</trendpoints>";
/*  88 */     htmlstr = htmlstr + "<annotations autoScale=\"0\">";
/*  89 */     htmlstr = htmlstr + "<annotationGroup id=\"Grp1\" showBelow=\"0\" xScale=\"200\" yScale=\"120\" x=\"164\" y=\"157\">";
/*  90 */     htmlstr = htmlstr + "<annotation type=\"circle\" x=\"0\" y=\"0\" color=\"FFFFFF\" alpha=\"15\" radius=\"7\" />";
/*  91 */     htmlstr = htmlstr + "</annotationGroup></annotations><styles>";
/*  92 */     htmlstr = htmlstr + "<definition>";
/*  93 */     htmlstr = htmlstr + "<style name=\"pivotGlow\" type=\"glow\" color=\"F48900\" blurX=\"15\" blurY=\"15\" alpha=\"60\" />";
/*  94 */     htmlstr = htmlstr + "<style name=\"circleBlur\" type=\"blur\" />";
/*  95 */     htmlstr = htmlstr + "<style name=\"TTipFont\" type=\"font\" color=\"F48900\" bgColor=\"000000\" borderColor=\"F48900\" font=\"Verdana\" size=\"10\" />";
/*  96 */     htmlstr = htmlstr + "</definition><application>";
/*  97 */     htmlstr = htmlstr + "<apply toObject=\"PIVOT\" styles=\"pivotGlow\" />";
/*  98 */     htmlstr = htmlstr + "<apply toObject=\"Grp1\" styles=\"circleBlur\" />";
/*  99 */     htmlstr = htmlstr + "<apply toObject=\"TOOLTIP\" styles=\"TTipFont\" />";
/* 100 */     htmlstr = htmlstr + "</application></styles></chart>";
/*     */     try
/*     */     {
/* 104 */       out.println(htmlstr);
/*     */     }
/*     */     catch (Exception e) {
/* 107 */       out.println("error");
/*     */     }
/* 109 */     out.flush();
/* 110 */     out.close();
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.ReportContent
 * JD-Core Version:    0.5.3
 */