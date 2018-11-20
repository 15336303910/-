/*     */ package base.util;
/*     */ 
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import freemarker.template.TemplateException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public final class PageHelper
/*     */ {
/*  21 */   private static Log log = LogFactory.getLog(PageHelper.class);
/*     */ 
/*     */   public static String getPageBar(Page page) {
/*  24 */     String firstUrl = "";
/*  25 */     String prevUrl = "";
/*  26 */     String nextUrl = "";
/*  27 */     String lastUrl = "";
/*  28 */     String numPage = "";
/*  29 */     String jumpPage = "";
/*     */ 
/*  31 */     boolean isHasParam = true;
/*     */ 
/*  33 */     if ((page.getParam() == null) || ("".equals(page.getParam()))) {
/*  34 */       isHasParam = false;
/*     */     }
/*     */ 
/*  37 */     if ((page.getTotalPageCount() > 1) && (page.getCurPage() != 1)) {
/*  38 */       firstUrl = page.getUrl() + "?page=1";
/*  39 */       if (isHasParam) {
/*  40 */         firstUrl = firstUrl + "&" + page.getParam();
/*     */       }
/*     */     }
/*     */ 
/*  44 */     if (page.hasPreviousPage()) {
/*  45 */       prevUrl = page.getUrl() + "?page=" + (page.getCurPage() - 1);
/*  46 */       if (isHasParam) {
/*  47 */         prevUrl = prevUrl + "&" + page.getParam();
/*     */       }
/*     */     }
/*     */ 
/*  51 */     if (page.hasNextPage()) {
/*  52 */       nextUrl = page.getUrl() + "?page=" + (page.getCurPage() + 1);
/*  53 */       if (isHasParam) {
/*  54 */         nextUrl = nextUrl + "&" + page.getParam();
/*     */       }
/*     */     }
/*     */ 
/*  58 */     if ((page.getTotalPageCount() > 1) && (page.getCurPage() < page.getTotalPageCount())) {
/*  59 */       lastUrl = page.getUrl() + "?page=" + page.getTotalPageCount();
/*  60 */       if (isHasParam) {
/*  61 */         lastUrl = lastUrl + "&" + page.getParam();
/*     */       }
/*     */     }
/*     */ 
/*  65 */     numPage = getNumPage(page.getPageSize(), page.getCNum());
/*  66 */     jumpPage = getJumpPage(page.getTotalPageCount(), page.getCurPage());
/*     */ 
/*  68 */     Map root = new HashMap();
/*     */ 
/*  70 */     root.put("start", Integer.valueOf(page.getStartOfPage()));
/*  71 */     root.put("pageSize", Integer.valueOf(page.getPageSize()));
/*  72 */     root.put("total", Integer.valueOf(page.getTotalCount()));
/*  73 */     root.put("page", Integer.valueOf(page.getCurPage()));
/*  74 */     root.put("totalPageCount", Integer.valueOf(page.getTotalPageCount()));
/*  75 */     root.put("url", page.getUrl());
/*  76 */     root.put("param", page.getParam());
/*  77 */     root.put("firstUrl", firstUrl);
/*  78 */     root.put("prevUrl", prevUrl);
/*  79 */     root.put("nextUrl", nextUrl);
/*  80 */     root.put("lastUrl", lastUrl);
/*  81 */     root.put("numPage", numPage);
/*  82 */     root.put("jumpPage", jumpPage);
/*  83 */     root.put("numParam", page.parseParam(page.getParam(), "pageSize="));
/*     */ 
/*  85 */     Configuration config = new Configuration();
/*     */ 
/*  92 */     config.setNumberFormat("##########");
/*     */ 
/*  95 */     config.setClassForTemplateLoading(PageHelper.class, "");
/*  96 */     config.setEncoding(Locale.getDefault(), "utf-8");
/*     */ 
/*  98 */     StringWriter out = new StringWriter();
/*     */     try
/*     */     {
/* 102 */       Template template = config.getTemplate(page.getPageTemplate());
/* 103 */       template.setEncoding("utf-8");
/* 104 */       template.process(root, out);
/*     */     } catch (IOException e) {
/* 106 */       log.error(e.getMessage());
/*     */     } catch (TemplateException e) {
/* 108 */       log.error(e.getMessage());
/*     */     }
/*     */ 
/* 111 */     return out.toString();
/*     */   }
/*     */ 
/*     */   private static String getNumPage(int pageSize, int cNum) {
/* 115 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 117 */     for (int i = 1; i <= cNum; ++i) {
/* 118 */       int tmp = i * 10;
/* 119 */       sb.append("<option value='").append(tmp).append("'");
/* 120 */       if (tmp == pageSize) {
/* 121 */         sb.append(" selected ");
/*     */       }
/* 123 */       sb.append(">").append(tmp).append("</option>");
/*     */     }
/*     */ 
/* 126 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String getJumpPage(long totalPageCount, int curPage) {
/* 130 */     StringBuffer sb = new StringBuffer();
/* 131 */     for (int i = 1; i <= totalPageCount; ++i) {
/* 132 */       sb.append("<option value='").append(i).append("'");
/* 133 */       if (i == curPage) {
/* 134 */         sb.append(" selected ");
/*     */       }
/* 136 */       sb.append(">").append(i).append("</option>");
/*     */     }
/* 138 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 142 */     Page page = new Page();
/* 143 */     page.setCurPage(2);
/* 144 */     page.setTotalCount(10);
/* 145 */     page.setQueryString("/wbdtd/media_browser.shtml?media.name=test");
/* 146 */     System.out.println(getPageBar(page));
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.PageHelper
 * JD-Core Version:    0.5.3
 */