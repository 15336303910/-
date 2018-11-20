/*    */ package base.database.impl;
/*    */ 
/*    */ import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
/*    */ import com.ibatis.sqlmap.engine.mapping.statement.SelectStatement;
/*    */ 
/*    */ public class CountStatementUtil
/*    */ {
/*    */   public static MappedStatement createCountStatement(MappedStatement selectStatement)
/*    */   {
/* 25 */     return new CountStatement((SelectStatement)selectStatement);
/*    */   }
/*    */ 
/*    */   public static String getCountStatementId(String selectStatementId) {
/* 29 */     return "__" + selectStatementId + "Count__";
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.database.impl.CountStatementUtil
 * JD-Core Version:    0.5.3
 */