/*     */ package base.util;
/*     */ 
/*     */ public class Page
/*     */ {
/*     */   public static final int DEFAULT_PAGE_SIZE = 10;
/*     */   public static final String PAGE_PREFIX = "page=";
/*     */   public static final String PAGE_SIZE_PREFIX = "pageSize=";
/*     */   private int curPage;
/*     */   private int pageSize;
/*     */   private int totalCount;
/*     */   private int cNum;
/*     */   private String url;
/*     */   private String param;
/*     */   private String pageTemplate;
/*     */ 
/*     */   public Page()
/*     */   {
/*  25 */     this(1, 0, 10, "");
/*     */   }
/*     */ 
/*     */   public Page(int curPage, int totalSize, String queryString) {
/*  29 */     this(curPage, totalSize, 10, queryString);
/*     */   }
/*     */ 
/*     */   public Page(int curPage, int totalSize, int pageSize, String queryString)
/*     */   {
/*  10 */     this.curPage = 1;
/*     */ 
/*  12 */     this.pageSize = 10;
/*     */ 
/*  16 */     this.cNum = 10;
/*     */ 
/*  18 */     this.url = "";
/*     */ 
/*  20 */     this.param = "";
/*     */ 
/*  22 */     this.pageTemplate = "page.ftl";
/*     */ 
/*  33 */     this.pageSize = pageSize;
/*  34 */     this.totalCount = totalSize;
/*  35 */     this.curPage = setCurrentPage(curPage);
/*  36 */     parseQueryString(queryString);
/*     */   }
/*     */ 
/*     */   public Page(int curPage, int totalSize, int pageSize, String url, String param)
/*     */   {
/*  10 */     this.curPage = 1;
/*     */ 
/*  12 */     this.pageSize = 10;
/*     */ 
/*  16 */     this.cNum = 10;
/*     */ 
/*  18 */     this.url = "";
/*     */ 
/*  20 */     this.param = "";
/*     */ 
/*  22 */     this.pageTemplate = "page.ftl";
/*     */ 
/*  40 */     this.pageSize = pageSize;
/*  41 */     this.totalCount = totalSize;
/*  42 */     this.curPage = setCurrentPage(curPage);
/*  43 */     this.url = url;
/*  44 */     this.param = parseParam(param, "page=");
/*     */   }
/*     */ 
/*     */   private int setCurrentPage(int currentPage) {
/*  48 */     if (currentPage > getTotalPageCount()) {
/*  49 */       return getTotalPageCount();
/*     */     }
/*  51 */     return currentPage;
/*     */   }
/*     */ 
/*     */   public String parseParam(String para, String f)
/*     */   {
/*  56 */     StringBuffer sb = new StringBuffer();
/*  57 */     if (para != null) {
/*  58 */       String[] params = para.split("&");
/*  59 */       if (params != null) {
/*  60 */         int i = 0; for (int len = params.length; i < len; ++i) {
/*  61 */           if (params[i].indexOf(f) != -1) {
/*     */             continue;
/*     */           }
/*  64 */           if (params[i].length() > 0) {
/*  65 */             sb.append(params[i]);
/*  66 */             if (i < len - 1) {
/*  67 */               sb.append("&");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  73 */     String paramTmp = sb.toString();
/*  74 */     if (paramTmp.endsWith("&")) {
/*  75 */       paramTmp = paramTmp.substring(0, paramTmp.length() - 1);
/*     */     }
/*     */ 
/*  78 */     return paramTmp;
/*     */   }
/*     */ 
/*     */   private void parseQueryString(String queryString) {
/*  82 */     if (queryString.indexOf("?") != -1) {
/*  83 */       String[] tmps = queryString.split("\\?");
/*  84 */       this.url = tmps[0];
/*  85 */       this.param = tmps[1];
/*     */     } else {
/*  87 */       this.url = queryString;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTotalCount() {
/*  92 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public int getTotalPageCount() {
/*  96 */     if (this.totalCount % this.pageSize == 0) {
/*  97 */       return (this.totalCount / this.pageSize);
/*     */     }
/*  99 */     return (this.totalCount / this.pageSize + 1);
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/* 104 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public boolean hasNextPage() {
/* 108 */     return (getCurPage() < getTotalPageCount());
/*     */   }
/*     */ 
/*     */   public boolean hasPreviousPage() {
/* 112 */     return (getCurPage() > 1);
/*     */   }
/*     */ 
/*     */   public int getStartOfPage() {
/* 116 */     return getStartOfPage(this.curPage, this.pageSize);
/*     */   }
/*     */ 
/*     */   public int getStartOfPage(int pageNo) {
/* 120 */     return getStartOfPage(pageNo, 10);
/*     */   }
/*     */ 
/*     */   public int getStartOfPage(int pageNo, int pSize) {
/* 124 */     return ((pageNo - 1) * pSize);
/*     */   }
/*     */ 
/*     */   public int getCNum()
/*     */   {
/* 131 */     return this.cNum;
/*     */   }
/*     */ 
/*     */   public void setCNum(int num)
/*     */   {
/* 138 */     this.cNum = num;
/*     */   }
/*     */ 
/*     */   public String getPageTemplate()
/*     */   {
/* 145 */     return this.pageTemplate;
/*     */   }
/*     */ 
/*     */   public void setPageTemplate(String pageTemplate)
/*     */   {
/* 152 */     this.pageTemplate = pageTemplate;
/*     */   }
/*     */ 
/*     */   public int getCurPage()
/*     */   {
/* 159 */     return this.curPage;
/*     */   }
/*     */ 
/*     */   public void setCurPage(int curPage)
/*     */   {
/* 166 */     this.curPage = curPage;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 173 */     return this.url;
/*     */   }
/*     */ 
/*     */   public String getParam()
/*     */   {
/* 180 */     return this.param;
/*     */   }
/*     */ 
/*     */   public void setQueryString(String queryString) {
/* 184 */     parseQueryString(queryString);
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize)
/*     */   {
/* 191 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(int totalCount)
/*     */   {
/* 198 */     this.totalCount = totalCount;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.Page
 * JD-Core Version:    0.5.3
 */