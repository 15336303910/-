/*    */ package base.database.impl;
/*    */ 
/*    */ import base.util.PropertiesUtil;
/*    */ import com.ibatis.sqlmap.engine.execution.SqlExecutor;
/*    */ import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
/*    */ import com.ibatis.sqlmap.engine.scope.StatementScope;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class LimitSqlExecutor extends SqlExecutor
/*    */ {
/* 17 */   private static final Log logger = LogFactory.getLog(LimitSqlExecutor.class);
/*    */   private Dialect dialect;
/* 21 */   private boolean enableLimit = true;
/*    */ 
/*    */   public Dialect getDialect() {
/* 24 */     return this.dialect;
/*    */   }
/*    */ 
/*    */   public void setDialect(Dialect dialect) {
/* 28 */     this.dialect = dialect;
/*    */   }
/*    */ 
/*    */   public boolean isEnableLimit() {
/* 32 */     return this.enableLimit;
/*    */   }
/*    */ 
/*    */   public void setEnableLimit(boolean enableLimit) {
/* 36 */     this.enableLimit = enableLimit;
/*    */   }
/*    */ 
/*    */   public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters, int skipResults, int maxResults, RowHandlerCallback callback)
/*    */     throws SQLException
/*    */   {
/* 43 */     if ((((skipResults != 0) || (maxResults != -999999))) && (supportsLimit()))
/*    */     {
/* 45 */       Properties properties = PropertiesUtil.getInstance().getConfigInfo();
/* 46 */       if (properties.getProperty("db.type") == null) {
/* 47 */         logger.error("未配置数据库类型");
/* 48 */         return;
/*    */       }
/* 50 */       String dbType = properties.getProperty("db.type");
/* 51 */       if (dbType.toLowerCase().equals("mysql"))
/* 52 */         sql = this.dialect.getMySqlLimit(sql, skipResults, maxResults);
/* 53 */       else if (dbType.toLowerCase().equals("oracle")) {
/* 54 */         sql = this.dialect.getOracleLimit(sql, skipResults, maxResults);
/*    */       }
/*    */ 
/* 57 */       skipResults = 0;
/* 58 */       maxResults = -999999;
/*    */     }
/* 60 */     super.executeQuery(statementScope, conn, sql, parameters, skipResults, 
/* 61 */       maxResults, callback);
/*    */   }
/*    */ 
/*    */   public boolean supportsLimit() {
/* 65 */     if ((this.enableLimit) && (this.dialect != null)) {
/* 66 */       return this.dialect.supportsLimit();
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.database.impl.LimitSqlExecutor
 * JD-Core Version:    0.5.3
 */