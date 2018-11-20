/*    */ package base.database.impl;
/*    */ 
/*    */ public class SqlDialect
/*    */   implements Dialect
/*    */ {
/*    */   protected static final String SQL_END_DELIMITER = ";";
/*    */ 
/*    */   public String getMySqlLimit(String sql, int offset, int limit)
/*    */   {
/* 17 */     sql = trim(sql);
/* 18 */     StringBuffer sb = new StringBuffer(sql.length() + 20);
/* 19 */     sb.append(sql);
/* 20 */     if (offset > 0)
/* 21 */       sb.append(" limit ").append(offset).append(',').append(limit).append(";");
/*    */     else {
/* 23 */       sb.append(" limit ").append(limit).append(";");
/*    */     }
/* 25 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public String getOracleLimit(String sql, int offset, int limit)
/*    */   {
/* 30 */     if (offset == 1) {
/* 31 */       offset = 0;
/*    */     }
/* 33 */     StringBuffer pageStr = new StringBuffer();
/* 34 */     pageStr.append("select * from ( select row_limit.*, rownum rownum_ from (");
/* 35 */     pageStr.append(trim(sql));
/* 36 */     pageStr.append(" ) row_limit where rownum <= ");
/* 37 */     pageStr.append(limit + offset);
/* 38 */     pageStr.append(" ) where rownum_ >");
/* 39 */     pageStr.append(offset);
/* 40 */     return pageStr.toString();
/*    */   }
/*    */ 
/*    */   public boolean supportsLimit()
/*    */   {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   private String trim(String sql) {
/* 49 */     sql = sql.trim();
/* 50 */     if (sql.endsWith(";")) {
/* 51 */       sql = sql.substring(0, sql.length() - 1 - ";".length());
/*    */     }
/* 53 */     return sql;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.database.impl.SqlDialect
 * JD-Core Version:    0.5.3
 */